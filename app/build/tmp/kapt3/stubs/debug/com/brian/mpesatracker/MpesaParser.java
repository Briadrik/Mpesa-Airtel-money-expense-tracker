package com.brian.mpesatracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\u0010\bJ \u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bH\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\u0006H\u0002J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0006J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u0006\u00a8\u0006\u0016"}, d2 = {"Lcom/brian/mpesatracker/MpesaParser;", "", "()V", "extractAmount", "", "text", "", "label", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Double;", "extractName", "keywords", "", "extractPaybillName", "extractTxnCode", "msg", "firstAmount", "isMpesaMessage", "", "sender", "body", "parse", "Lcom/brian/mpesatracker/ParsedMpesa;", "app_debug"})
public final class MpesaParser {
    @org.jetbrains.annotations.NotNull()
    public static final com.brian.mpesatracker.MpesaParser INSTANCE = null;
    
    private MpesaParser() {
        super();
    }
    
    private final java.lang.Double extractAmount(java.lang.String text, java.lang.String label) {
        return null;
    }
    
    private final double firstAmount(java.lang.String text) {
        return 0.0;
    }
    
    public final boolean isMpesaMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String sender, @org.jetbrains.annotations.NotNull()
    java.lang.String body) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.brian.mpesatracker.ParsedMpesa parse(@org.jetbrains.annotations.NotNull()
    java.lang.String body) {
        return null;
    }
    
    private final java.lang.String extractTxnCode(java.lang.String msg) {
        return null;
    }
    
    private final java.lang.String extractName(java.lang.String text, java.util.List<java.lang.String> keywords) {
        return null;
    }
    
    private final java.lang.String extractPaybillName(java.lang.String text) {
        return null;
    }
}