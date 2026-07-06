package com.brian.mpesatracker.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE timestamp >= :from ORDER BY timestamp DESC")
    fun getAllSince(from: Long): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Transaction?

    // Category spend breakdown with date filter
    @Query("""
        SELECT category, SUM(amount) as total 
        FROM transactions 
        WHERE direction = 'EXPENSE' AND category IS NOT NULL AND timestamp >= :from
        GROUP BY category
    """)
    fun getSpendByCategory(from: Long): LiveData<List<CategoryTotal>>

    // Total spend (expenses only) with date filter
    @Query("SELECT SUM(amount) FROM transactions WHERE direction = 'EXPENSE' AND timestamp >= :from")
    fun getTotalSpend(from: Long): LiveData<Double?>

    // Total income with date filter
    @Query("SELECT SUM(amount) FROM transactions WHERE direction = 'INCOME' AND timestamp >= :from")
    fun getTotalIncome(from: Long): LiveData<Double?>

    // Total fees paid with date filter
    @Query("SELECT SUM(cost) FROM transactions WHERE timestamp >= :from")
    fun getTotalFees(from: Long): LiveData<Double?>

    // Uncategorized expense count
    @Query("SELECT COUNT(*) FROM transactions WHERE direction = 'EXPENSE' AND category IS NULL")
    fun getUncategorizedCount(): LiveData<Int>

    // Auto-suggest: last category used for a given counterparty
    @Query("""
        SELECT category FROM transactions 
        WHERE counterparty = :counterparty AND category IS NOT NULL 
        ORDER BY timestamp DESC LIMIT 1
    """)
    suspend fun getLastCategoryForCounterparty(counterparty: String): String?

    // Fuliza usage tracking
    @Query("SELECT SUM(amount) FROM transactions WHERE txnType = 'FULIZA' AND timestamp >= :from")
    fun getTotalFuliza(from: Long): LiveData<Double?>
}

data class CategoryTotal(
    val category: String,
    val total: Double
)
