package com.brian.mpesatracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BS\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000eJ\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\bH\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\"\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003Jl\u0010&\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010\'J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020,H\u00d6\u0001J\t\u0010-\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0015\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016\u00a8\u0006."}, d2 = {"Lcom/brian/mpesatracker/ParsedMpesa;", "", "txnCode", "", "amount", "", "counterparty", "direction", "Lcom/brian/mpesatracker/TxnDirection;", "txnType", "balance", "cost", "rawMessage", "provider", "(Ljava/lang/String;DLjava/lang/String;Lcom/brian/mpesatracker/TxnDirection;Ljava/lang/String;Ljava/lang/Double;DLjava/lang/String;Ljava/lang/String;)V", "getAmount", "()D", "getBalance", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getCost", "getCounterparty", "()Ljava/lang/String;", "getDirection", "()Lcom/brian/mpesatracker/TxnDirection;", "getProvider", "getRawMessage", "getTxnCode", "getTxnType", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;DLjava/lang/String;Lcom/brian/mpesatracker/TxnDirection;Ljava/lang/String;Ljava/lang/Double;DLjava/lang/String;Ljava/lang/String;)Lcom/brian/mpesatracker/ParsedMpesa;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class ParsedMpesa {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String txnCode = null;
    private final double amount = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String counterparty = null;
    @org.jetbrains.annotations.NotNull()
    private final com.brian.mpesatracker.TxnDirection direction = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String txnType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double balance = null;
    private final double cost = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String rawMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String provider = null;
    
    public ParsedMpesa(@org.jetbrains.annotations.Nullable()
    java.lang.String txnCode, double amount, @org.jetbrains.annotations.NotNull()
    java.lang.String counterparty, @org.jetbrains.annotations.NotNull()
    com.brian.mpesatracker.TxnDirection direction, @org.jetbrains.annotations.NotNull()
    java.lang.String txnType, @org.jetbrains.annotations.Nullable()
    java.lang.Double balance, double cost, @org.jetbrains.annotations.NotNull()
    java.lang.String rawMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String provider) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTxnCode() {
        return null;
    }
    
    public final double getAmount() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCounterparty() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.brian.mpesatracker.TxnDirection getDirection() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTxnType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getBalance() {
        return null;
    }
    
    public final double getCost() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRawMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProvider() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    public final double component2() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.brian.mpesatracker.TxnDirection component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
        return null;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.brian.mpesatracker.ParsedMpesa copy(@org.jetbrains.annotations.Nullable()
    java.lang.String txnCode, double amount, @org.jetbrains.annotations.NotNull()
    java.lang.String counterparty, @org.jetbrains.annotations.NotNull()
    com.brian.mpesatracker.TxnDirection direction, @org.jetbrains.annotations.NotNull()
    java.lang.String txnType, @org.jetbrains.annotations.Nullable()
    java.lang.Double balance, double cost, @org.jetbrains.annotations.NotNull()
    java.lang.String rawMessage, @org.jetbrains.annotations.NotNull()
    java.lang.String provider) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}