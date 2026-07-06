package com.brian.mpesatracker

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.brian.mpesatracker.data.AppDatabase
import com.brian.mpesatracker.databinding.ActivityCategorizeBinding
import kotlinx.coroutines.launch

class CategorizeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategorizeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txnId = intent.getLongExtra("txn_id", -1L)
        val suggestedCategory = intent.getStringExtra("suggested_category")
        if (txnId == -1L) { finish(); return }

        lifecycleScope.launch {
            val db = AppDatabase.getInstance(this@CategorizeActivity)
            val dao = db.transactionDao()
            val catDao = db.categoryDao()

            val txn = dao.getById(txnId) ?: run { finish(); return@launch }
            val categories = catDao.getAllSync()

            val dirLabel = if (txn.direction == "INCOME") "from" else "to"
            val typeTag = when (txn.txnType) {
                "FULIZA_USE"   -> " [Fuliza]"
                "FULIZA_REPAY" -> " [Fuliza repay]"
                "REVERSAL"     -> " [Reversal]"
                "DATA_BUNDLE"  -> " [Data]"
                else           -> ""
            }
            binding.summaryText.text =
                "Ksh${txn.amount} $dirLabel ${txn.counterparty}$typeTag"

            if (suggestedCategory != null) {
                binding.suggestText.text = "Last time: $suggestedCategory"
                binding.suggestText.visibility = android.view.View.VISIBLE
            } else {
                binding.suggestText.visibility = android.view.View.GONE
            }

            categories.forEach { cat ->
                val chip = layoutInflater.inflate(
                    R.layout.item_category_chip, binding.chipContainer, false
                ) as android.widget.Button

                chip.text = "${cat.emoji} ${cat.name}"

                // Highlight the suggested category
                if (cat.name == suggestedCategory) {
                    chip.setBackgroundColor(Color.parseColor("#1A73E8"))
                    chip.setTextColor(Color.WHITE)
                }

                chip.setOnClickListener {
                    lifecycleScope.launch {
                        dao.update(txn.copy(category = cat.name))
                        Toast.makeText(
                            this@CategorizeActivity,
                            "Saved as ${cat.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
                binding.chipContainer.addView(chip)
            }
        }
    }
}
