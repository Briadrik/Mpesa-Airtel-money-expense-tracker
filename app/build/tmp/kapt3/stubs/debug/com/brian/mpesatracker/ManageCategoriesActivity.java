package com.brian.mpesatracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0002J\b\u0010\r\u001a\u00020\u000bH\u0002J\u0012\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u000bH\u0002J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/brian/mpesatracker/ManageCategoriesActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/brian/mpesatracker/CategoriesAdapter;", "categories", "", "Lcom/brian/mpesatracker/data/Category;", "recycler", "Landroidx/recyclerview/widget/RecyclerView;", "confirmDelete", "", "cat", "loadCategories", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "showAddDialog", "showRenameDialog", "app_debug"})
public final class ManageCategoriesActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView recycler;
    private com.brian.mpesatracker.CategoriesAdapter adapter;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.brian.mpesatracker.data.Category> categories = null;
    
    public ManageCategoriesActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadCategories() {
    }
    
    private final void showAddDialog() {
    }
    
    private final void showRenameDialog(com.brian.mpesatracker.data.Category cat) {
    }
    
    private final void confirmDelete(com.brian.mpesatracker.data.Category cat) {
    }
}