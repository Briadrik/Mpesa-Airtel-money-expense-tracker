package com.brian.mpesatracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0014J\u0014\u0010\u001f\u001a\u00020\u001c2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00110\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/brian/mpesatracker/BarChartView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "amtColW", "", "amtPaint", "Landroid/graphics/Paint;", "barColors", "", "", "barPaint", "bgPaint", "cornerR", "d", "data", "Lcom/brian/mpesatracker/data/CategoryTotal;", "gap", "labelAmtGap", "labelW", "rowH", "textPaint", "Landroid/text/TextPaint;", "formatAmt", "v", "", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "setData", "items", "app_debug"})
public final class BarChartView extends android.view.View {
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.brian.mpesatracker.data.CategoryTotal> data;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> barColors = null;
    private final float d = 0.0F;
    private final float rowH = 0.0F;
    private final float gap = 0.0F;
    private final float cornerR = 0.0F;
    private final float labelAmtGap = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint barPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.text.TextPaint textPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint amtPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint bgPaint = null;
    private float labelW = 0.0F;
    private float amtColW = 0.0F;
    
    public BarChartView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.List<com.brian.mpesatracker.data.CategoryTotal> items) {
    }
    
    private final java.lang.String formatAmt(double v) {
        return null;
    }
    
    @java.lang.Override()
    protected void onDraw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas) {
    }
}