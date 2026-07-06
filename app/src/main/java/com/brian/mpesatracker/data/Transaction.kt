package com.brian.mpesatracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val txnCode: String?,
    val amount: Double,
    val counterparty: String,
    val direction: String,      // EXPENSE, INCOME, FAILED, UNKNOWN
    val txnType: String,        // SEND, PAYBILL, BUY_GOODS, WITHDRAW, AIRTIME, DATA_BUNDLE,
                                // RECEIVE, FULIZA_USE, FULIZA_REPAY, REVERSAL, OTHER
    val category: String?,      // null until user categorizes it
    val balance: Double?,
    val cost: Double,
    val rawMessage: String,
    val timestamp: Long,
    val provider: String = "MPESA"   // "MPESA" or "AIRTEL"
)
