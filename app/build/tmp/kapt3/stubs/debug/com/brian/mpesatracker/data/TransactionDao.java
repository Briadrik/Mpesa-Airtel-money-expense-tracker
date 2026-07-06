package com.brian.mpesatracker.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u001c\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0018\u0010\t\u001a\u0004\u0018\u00010\u00052\u0006\u0010\n\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00040\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0018\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0018\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0018\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0018\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0003H\'J\u0016\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001a\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001b\u00a8\u0006\u001e"}, d2 = {"Lcom/brian/mpesatracker/data/TransactionDao;", "", "getAll", "Landroidx/lifecycle/LiveData;", "", "Lcom/brian/mpesatracker/data/Transaction;", "getAllSince", "from", "", "getById", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLastCategoryForCounterparty", "", "counterparty", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSpendByCategory", "Lcom/brian/mpesatracker/data/CategoryTotal;", "getTotalFees", "", "getTotalFuliza", "getTotalIncome", "getTotalSpend", "getUncategorizedCount", "", "insert", "transaction", "(Lcom/brian/mpesatracker/data/Transaction;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "", "app_debug"})
@androidx.room.Dao()
public abstract interface TransactionDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.brian.mpesatracker.data.Transaction transaction, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.brian.mpesatracker.data.Transaction transaction, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM transactions ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.brian.mpesatracker.data.Transaction>> getAll();
    
    @androidx.room.Query(value = "SELECT * FROM transactions WHERE timestamp >= :from ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.brian.mpesatracker.data.Transaction>> getAllSince(long from);
    
    @androidx.room.Query(value = "SELECT * FROM transactions WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.brian.mpesatracker.data.Transaction> $completion);
    
    @androidx.room.Query(value = "\n        SELECT category, SUM(amount) as total \n        FROM transactions \n        WHERE direction = \'EXPENSE\' AND category IS NOT NULL AND timestamp >= :from\n        GROUP BY category\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.brian.mpesatracker.data.CategoryTotal>> getSpendByCategory(long from);
    
    @androidx.room.Query(value = "SELECT SUM(amount) FROM transactions WHERE direction = \'EXPENSE\' AND timestamp >= :from")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Double> getTotalSpend(long from);
    
    @androidx.room.Query(value = "SELECT SUM(amount) FROM transactions WHERE direction = \'INCOME\' AND timestamp >= :from")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Double> getTotalIncome(long from);
    
    @androidx.room.Query(value = "SELECT SUM(cost) FROM transactions WHERE timestamp >= :from")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Double> getTotalFees(long from);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM transactions WHERE direction = \'EXPENSE\' AND category IS NULL")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Integer> getUncategorizedCount();
    
    @androidx.room.Query(value = "\n        SELECT category FROM transactions \n        WHERE counterparty = :counterparty AND category IS NOT NULL \n        ORDER BY timestamp DESC LIMIT 1\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLastCategoryForCounterparty(@org.jetbrains.annotations.NotNull()
    java.lang.String counterparty, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(amount) FROM transactions WHERE txnType = \'FULIZA\' AND timestamp >= :from")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.lang.Double> getTotalFuliza(long from);
}