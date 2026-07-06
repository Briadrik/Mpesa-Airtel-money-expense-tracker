package com.brian.mpesatracker

import java.util.regex.Pattern

enum class TxnDirection { EXPENSE, INCOME, FAILED, UNKNOWN }

data class ParsedMpesa(
    val txnCode: String?,
    val amount: Double,
    val counterparty: String,
    val direction: TxnDirection,
    val txnType: String,        // fine-grained type
    val balance: Double?,
    val cost: Double,
    val rawMessage: String,
    val provider: String = "MPESA"   // "MPESA" or "AIRTEL"
)

object MoneyParserUtils {
    // Pulls the name/entity right after a keyword, stopping at date marker / phone / period
    fun extractName(text: String, keywords: List<String>): String? {
        for (kw in keywords) {
            val idx = text.indexOf(kw, ignoreCase = true)
            if (idx == -1) continue
            val after = text.substring(idx + kw.length).trim()
            val cut = Regex("^([A-Za-z .]+?)(?=\\s+\\d|\\s+on\\s|\\.|,|$)").find(after)
            val name = cut?.groupValues?.get(1)?.trim()
            if (!name.isNullOrBlank()) return name
        }
        return null
    }
}

object MpesaParser {

    // Matches "Ksh1,234.50" or "KSH105.00" -> returns 1234.50
    private fun extractAmount(text: String, label: String): Double? {
        val pattern = Pattern.compile(
            "$label\\s*[Kk][Ss][Hh]\\s?([\\d,]+\\.\\d{2})",
            Pattern.CASE_INSENSITIVE
        )
        val m = pattern.matcher(text)
        return if (m.find()) m.group(1)?.replace(",", "")?.toDoubleOrNull() else null
    }

    private fun firstAmount(text: String): Double {
        val m = Pattern.compile("[Kk][Ss][Hh]\\s?([\\d,]+\\.\\d{2})").matcher(text)
        return if (m.find()) m.group(1)!!.replace(",", "").toDouble() else 0.0
    }

    fun isMpesaMessage(sender: String, body: String): Boolean {
        return sender.contains("MPESA", ignoreCase = true) ||
                body.contains("M-PESA", ignoreCase = true)
    }

    fun parse(body: String): ParsedMpesa {
        val msg = body.trim()

        // ── Failed transaction ──────────────────────────────────────────────
        if (msg.startsWith("Failed", ignoreCase = true)) {
            return ParsedMpesa(
                txnCode = null,
                amount = firstAmount(msg),
                counterparty = extractName(msg, listOf("to", "from")) ?: "Unknown",
                direction = TxnDirection.FAILED,
                txnType = "FAILED",
                balance = extractAmount(msg, "balance is"),
                cost = 0.0,
                rawMessage = msg
            )
        }

        // ── Reversal ────────────────────────────────────────────────────────
        // "M-PESA reversal of Ksh500.00 to JOHN DOE"  OR  "Your transaction has been reversed"
        if (msg.contains("reversal", ignoreCase = true) ||
            msg.contains("reversed", ignoreCase = true)) {
            val amount = firstAmount(msg)
            return ParsedMpesa(
                txnCode = extractTxnCode(msg),
                amount = amount,
                counterparty = extractName(msg, listOf("to", "from")) ?: "Reversal",
                direction = if (msg.contains("credited", ignoreCase = true) ||
                    msg.contains("received", ignoreCase = true))
                    TxnDirection.INCOME else TxnDirection.UNKNOWN,
                txnType = "REVERSAL",
                balance = extractAmount(msg, "balance is"),
                cost = 0.0,
                rawMessage = msg
            )
        }

        // ── Fuliza (M-PESA overdraft) ────────────────────────────────────────
        // Draw: "Fuliza M-PESA amount is Ksh50.00. Access Fee charged..."
        // Repay (auto, from balance): "Ksh446.66 from your M-PESA has been used
        //   to fully/partially pay your outstanding Fuliza M-PESA"
        // Repay (explicit): "Fuliza M-PESA loan of Ksh200.00 has been repaid"
        if (msg.contains("Fuliza", ignoreCase = true)) {
            val amount = firstAmount(msg)
            val isRepay = msg.contains("repaid", ignoreCase = true) ||
                    msg.contains("repayment", ignoreCase = true) ||
                    msg.contains("used to fully pay", ignoreCase = true) ||
                    msg.contains("used to partially pay", ignoreCase = true)
            return ParsedMpesa(
                txnCode = extractTxnCode(msg),
                amount = amount,
                counterparty = "Fuliza M-PESA",
                direction = if (isRepay) TxnDirection.EXPENSE else TxnDirection.INCOME,
                txnType = if (isRepay) "FULIZA_REPAY" else "FULIZA_USE",
                balance = extractAmount(msg, "balance is"),
                cost = extractAmount(msg, "Transaction cost,?") ?: 0.0,
                rawMessage = msg
            )
        }

        // ── M-Shwari transfer ─────────────────────────────────────────────────
        // "Ksh500.00 transferred from M-Shwari account on ... M-Shwari balance is
        //  Ksh5,510.18 .M-PESA balance is Ksh500.00"
        if (msg.contains("M-Shwari", ignoreCase = true) && msg.contains("transferred", ignoreCase = true)) {
            val toMpesa = msg.contains("from M-Shwari", ignoreCase = true)
            return ParsedMpesa(
                txnCode = extractTxnCode(msg),
                amount = firstAmount(msg),
                counterparty = "M-Shwari",
                direction = if (toMpesa) TxnDirection.INCOME else TxnDirection.EXPENSE,
                txnType = "MSHWARI_TRANSFER",
                balance = extractAmount(msg, "M-PESA balance is"),
                cost = extractAmount(msg, "Transaction cost,?") ?: 0.0,
                rawMessage = msg
            )
        }

        // ── Data bundle purchase ─────────────────────────────────────────────
        // "You have successfully purchased a data bundle of XMB for Ksh50.00"
        if (msg.contains("data bundle", ignoreCase = true) ||
            msg.contains("data pack", ignoreCase = true) ||
            (msg.contains("bundle", ignoreCase = true) && msg.contains("MB", ignoreCase = true))) {
            return ParsedMpesa(
                txnCode = extractTxnCode(msg),
                amount = firstAmount(msg),
                counterparty = "Data Bundle",
                direction = TxnDirection.EXPENSE,
                txnType = "DATA_BUNDLE",
                balance = extractAmount(msg, "balance is"),
                cost = extractAmount(msg, "Transaction cost,?") ?: 0.0,
                rawMessage = msg
            )
        }

        val txnCode = extractTxnCode(msg)
        val cost = extractAmount(msg, "Transaction cost,?") ?: 0.0
        val balance = extractAmount(msg, "balance is")

        return when {
            // ── Paybill ──────────────────────────────────────────────────────
            // "Ksh500.00 sent to KPLC TOKENS for account 123456"
            msg.contains("for account", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                extractPaybillName(msg) ?: "Paybill",
                TxnDirection.EXPENSE, "PAYBILL", balance, cost, msg
            )

            // ── Buy goods / Pochi / Till ──────────────────────────────────────
            msg.contains("paid to", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                extractName(msg, listOf("paid to")) ?: "Unknown",
                TxnDirection.EXPENSE, "BUY_GOODS", balance, cost, msg
            )

            // ── Send money ───────────────────────────────────────────────────
            msg.contains("sent to", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                extractName(msg, listOf("sent to")) ?: "Unknown",
                TxnDirection.EXPENSE, "SEND", balance, cost, msg
            )

            // ── Agent withdrawal ─────────────────────────────────────────────
            msg.contains("withdraw", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                extractName(msg, listOf("from")) ?: "Agent",
                TxnDirection.EXPENSE, "WITHDRAW", balance, cost, msg
            )

            // ── Airtime ──────────────────────────────────────────────────────
            msg.contains("airtime", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg), "Airtime",
                TxnDirection.EXPENSE, "AIRTIME", balance, cost, msg
            )

            // ── Receive money ────────────────────────────────────────────────
            msg.contains("received", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                extractName(msg, listOf("from")) ?: "Unknown",
                TxnDirection.INCOME, "RECEIVE", balance, cost, msg
            )

            // ── Salary / deposit ─────────────────────────────────────────────
            msg.contains("deposited", ignoreCase = true) -> ParsedMpesa(
                txnCode, firstAmount(msg),
                extractName(msg, listOf("by", "from")) ?: "Deposit",
                TxnDirection.INCOME, "RECEIVE", balance, cost, msg
            )

            // ── Fallback ─────────────────────────────────────────────────────
            else -> ParsedMpesa(
                txnCode, firstAmount(msg), "Unknown",
                TxnDirection.UNKNOWN, "OTHER", balance, cost, msg
            )
        }
    }

    private fun extractTxnCode(msg: String): String? {
        return Regex("^([A-Z0-9]{8,12})\\s+Confirmed").find(msg)?.groupValues?.get(1)
    }

    // Pulls the name/entity right after a keyword, stopping at date marker / phone / period
    private fun extractName(text: String, keywords: List<String>): String? {
        for (kw in keywords) {
            val idx = text.indexOf(kw, ignoreCase = true)
            if (idx == -1) continue
            val after = text.substring(idx + kw.length).trim()
            val cut = Regex("^([A-Za-z .]+?)(?=\\s+\\d|\\s+on\\s|\\.|,|$)").find(after)
            val name = cut?.groupValues?.get(1)?.trim()
            if (!name.isNullOrBlank()) return name
        }
        return null
    }

    // Paybill business name sits between "sent to"/"paid to" and "for account" —
    // e.g. "sent to LOOP BIZ for account 296051" -> "LOOP BIZ"
    private fun extractPaybillName(text: String): String? {
        val m = Regex(
            "(?:sent to|paid to)\\s+([A-Za-z0-9 .]+?)\\s+for account",
            RegexOption.IGNORE_CASE
        ).find(text)
        return m?.groupValues?.get(1)?.trim() ?: extractName(text, listOf("sent to", "paid to"))
    }
}
