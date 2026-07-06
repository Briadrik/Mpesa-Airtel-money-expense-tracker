package com.brian.mpesatracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\fH\u0002J\b\u0010\u001f\u001a\u00020\u000fH\u0002J\u0017\u0010 \u001a\u00020\u00162\b\u0010!\u001a\u0004\u0018\u00010\"H\u0002\u00a2\u0006\u0002\u0010#J\b\u0010$\u001a\u00020\nH\u0002J\b\u0010%\u001a\u00020&H\u0002J\u0012\u0010\'\u001a\u00020&2\b\u0010(\u001a\u0004\u0018\u00010)H\u0014J\b\u0010*\u001a\u00020&H\u0002J\b\u0010+\u001a\u00020\nH\u0002J\b\u0010,\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/brian/mpesatracker/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/brian/mpesatracker/TransactionAdapter;", "categoryBarChart", "Lcom/brian/mpesatracker/BarChartView;", "categoryBreakdownText", "Landroid/widget/TextView;", "currentPeriodStart", "", "dashboardScroll", "Landroid/widget/ScrollView;", "emptyText", "feesRow", "Landroid/widget/LinearLayout;", "fulizaRow", "historyPanel", "incomeRow", "permissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "totalFeesText", "totalFulizaText", "totalIncomeText", "totalSpendText", "uncategorizedBadge", "buildDashboard", "buildHistory", "fmt", "d", "", "(Ljava/lang/Double;)Ljava/lang/String;", "monthStart", "observeData", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "requestPermissionsIfNeeded", "weekStart", "yearStart", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.brian.mpesatracker.TransactionAdapter adapter;
    private android.widget.ScrollView dashboardScroll;
    private android.widget.LinearLayout historyPanel;
    private android.widget.TextView totalSpendText;
    private android.widget.LinearLayout incomeRow;
    private android.widget.TextView totalIncomeText;
    private android.widget.LinearLayout feesRow;
    private android.widget.TextView totalFeesText;
    private android.widget.LinearLayout fulizaRow;
    private android.widget.TextView totalFulizaText;
    private android.widget.TextView uncategorizedBadge;
    private com.brian.mpesatracker.BarChartView categoryBarChart;
    private android.widget.TextView categoryBreakdownText;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private android.widget.TextView emptyText;
    private long currentPeriodStart;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> permissionLauncher = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final android.widget.ScrollView buildDashboard() {
        return null;
    }
    
    private final android.widget.LinearLayout buildHistory() {
        return null;
    }
    
    private final void observeData() {
    }
    
    private final java.lang.String fmt(java.lang.Double d) {
        return null;
    }
    
    private final long weekStart() {
        return 0L;
    }
    
    private final long monthStart() {
        return 0L;
    }
    
    private final long yearStart() {
        return 0L;
    }
    
    private final void requestPermissionsIfNeeded() {
    }
}