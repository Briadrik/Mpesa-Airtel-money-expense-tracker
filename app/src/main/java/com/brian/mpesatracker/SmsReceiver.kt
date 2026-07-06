package com.brian.mpesatracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import androidx.core.app.NotificationCompat
import com.brian.mpesatracker.data.AppDatabase
import com.brian.mpesatracker.data.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        for (sms in messages) {
            val sender = sms.originatingAddress ?: ""
            val body = sms.messageBody ?: ""

            val parsed: ParsedMpesa = when {
                MpesaParser.isMpesaMessage(sender, body) -> MpesaParser.parse(body)
                AirtelMoneyParser.isAirtelMessage(sender, body) -> AirtelMoneyParser.parse(body)
                else -> continue
            }
            if (parsed.direction == TxnDirection.FAILED) continue

            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getInstance(context)
                val dao = db.transactionDao()

                // Auto-suggest: look up last category for this counterparty
                val suggestedCategory = dao.getLastCategoryForCounterparty(parsed.counterparty)

                val txn = Transaction(
                    txnCode = parsed.txnCode,
                    amount = parsed.amount,
                    counterparty = parsed.counterparty,
                    direction = parsed.direction.name,
                    txnType = parsed.txnType,
                    category = null,  // always null; user categorizes via notification
                    balance = parsed.balance,
                    cost = parsed.cost,
                    rawMessage = parsed.rawMessage,
                    timestamp = System.currentTimeMillis(),
                    provider = parsed.provider
                )
                val newId = dao.insert(txn)
                showCategorizeNotification(context, newId, parsed, suggestedCategory)
            }
        }
    }

    private fun showCategorizeNotification(
        context: Context,
        txnId: Long,
        parsed: ParsedMpesa,
        suggestedCategory: String?
    ) {
        val channelId = "mpesa_categorize"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(
                channelId, "M-Pesa Categorization",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        val openIntent = Intent(context, CategorizeActivity::class.java).apply {
            putExtra("txn_id", txnId)
            putExtra("suggested_category", suggestedCategory)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, txnId.toInt(), openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val providerLabel = if (parsed.provider == "AIRTEL") "Airtel Money" else "M-PESA"
        val label = if (parsed.direction == TxnDirection.INCOME) "from" else "to"
        val typeLabel = when (parsed.txnType) {
            "FULIZA_USE" -> "Fuliza used"
            "FULIZA_REPAY" -> "Fuliza repaid"
            "MSHWARI_TRANSFER" -> "M-Shwari transfer"
            "REVERSAL" -> "Reversal"
            "DATA_BUNDLE" -> "$providerLabel bundle"
            else -> "$providerLabel: Ksh${parsed.amount} $label ${parsed.counterparty}"
        }
        val subtitle = if (suggestedCategory != null)
            "Suggested: $suggestedCategory — tap to confirm or change"
        else
            "Tap to pick a category"

        val notification = androidx.core.app.NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.stat_notify_chat)
            .setContentTitle("Categorize: $typeLabel")
            .setContentText(subtitle)
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(txnId.toInt(), notification)
    }
}
