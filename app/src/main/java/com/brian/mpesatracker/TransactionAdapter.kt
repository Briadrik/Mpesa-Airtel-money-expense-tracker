package com.brian.mpesatracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brian.mpesatracker.data.Transaction
import com.brian.mpesatracker.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(
    private val onCategorizeClick: (Long) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private var items: List<Transaction> = emptyList()
    private val dateFmt = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())

    fun submitList(newItems: List<Transaction>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(txn: Transaction) {
            val sign = if (txn.direction == "INCOME") "+" else "-"
            val color = when (txn.direction) {
                "INCOME"  -> Color.parseColor("#2E7D32")  // dark green
                "EXPENSE" -> Color.parseColor("#C62828")  // dark red
                else      -> Color.GRAY
            }
            binding.amountText.text = "$sign Ksh${String.format("%,.2f", txn.amount)}"
            binding.amountText.setTextColor(color)

            // Provider badge: M-PESA (green) vs Airtel Money (red)
            if (txn.provider == "AIRTEL") {
                binding.providerBadge.text = "AIRTEL"
                binding.providerBadge.setTextColor(Color.parseColor("#E30613"))
            } else {
                binding.providerBadge.text = "M-PESA"
                binding.providerBadge.setTextColor(Color.parseColor("#00A651"))
            }

            // Show type tag for special transactions
            val typeTag = when (txn.txnType) {
                "FULIZA_USE"       -> " 🔴 Fuliza"
                "FULIZA_REPAY"     -> " ✅ Fuliza repay"
                "MSHWARI_TRANSFER" -> " 🏦 M-Shwari"
                "REVERSAL"         -> " 🔄 Reversal"
                "DATA_BUNDLE"      -> " 📶 Data"
                "AIRTIME"          -> " 📱 Airtime"
                "PAYBILL"          -> " 🏦 Paybill"
                "BUY_GOODS"        -> " 🏪 Till"
                "WITHDRAW"         -> " 🏧 Withdraw"
                else               -> ""
            }

            binding.counterpartyText.text = "${txn.counterparty}$typeTag"

            if (txn.category != null) {
                binding.categoryText.text = txn.category
                binding.categoryText.setTextColor(Color.parseColor("#555555"))
            } else {
                binding.categoryText.text = "Tag +"
                binding.categoryText.setTextColor(Color.parseColor("#1A73E8"))
                binding.categoryText.setOnClickListener {
                    onCategorizeClick(txn.id)
                }
            }

            // Show fee if non-zero
            if (txn.cost > 0) {
                binding.feeText.text = "Fee: Ksh${String.format("%.2f", txn.cost)}"
                binding.feeText.visibility = android.view.View.VISIBLE
            } else {
                binding.feeText.visibility = android.view.View.GONE
            }

            binding.timestampText.text = dateFmt.format(Date(txn.timestamp))
        }
    }
}
