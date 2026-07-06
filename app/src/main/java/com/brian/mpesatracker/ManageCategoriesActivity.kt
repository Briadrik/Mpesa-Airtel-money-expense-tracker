package com.brian.mpesatracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brian.mpesatracker.data.AppDatabase
import com.brian.mpesatracker.data.Category
import kotlinx.coroutines.launch

class ManageCategoriesActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: CategoriesAdapter
    private val categories = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val title = TextView(this).apply {
            text = "Manage Categories"
            textSize = 20f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setPadding(0, 0, 0, 24)
        }

        val addBtn = Button(this).apply {
            text = "+ Add Category"
        }

        recycler = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@ManageCategoriesActivity)
        }

        adapter = CategoriesAdapter(categories,
            onDelete = { cat -> confirmDelete(cat) },
            onRename = { cat -> showRenameDialog(cat) }
        )
        recycler.adapter = adapter

        layout.addView(title)
        layout.addView(addBtn)
        layout.addView(recycler)
        setContentView(layout)

        addBtn.setOnClickListener { showAddDialog() }
        loadCategories()
    }

    private fun loadCategories() {
        val dao = AppDatabase.getInstance(this).categoryDao()
        dao.getAll().observe(this) { list ->
            categories.clear()
            categories.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showAddDialog() {
        val input = EditText(this).apply { hint = "Category name" }
        AlertDialog.Builder(this)
            .setTitle("Add Category")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isNotEmpty()) {
                    lifecycleScope.launch {
                        AppDatabase.getInstance(this@ManageCategoriesActivity)
                            .categoryDao().insert(Category(name = name))
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showRenameDialog(cat: Category) {
        val input = EditText(this).apply { setText(cat.name) }
        AlertDialog.Builder(this)
            .setTitle("Rename Category")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isNotEmpty()) {
                    lifecycleScope.launch {
                        AppDatabase.getInstance(this@ManageCategoriesActivity)
                            .categoryDao().update(cat.copy(name = name))
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmDelete(cat: Category) {
        AlertDialog.Builder(this)
            .setTitle("Delete \"${cat.name}\"?")
            .setMessage("Transactions tagged with this category will show as uncategorized.")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    AppDatabase.getInstance(this@ManageCategoriesActivity)
                        .categoryDao().delete(cat)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

class CategoriesAdapter(
    private val items: List<Category>,
    private val onDelete: (Category) -> Unit,
    private val onRename: (Category) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.VH>() {

    inner class VH(val row: LinearLayout) : RecyclerView.ViewHolder(row)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val row = LinearLayout(parent.context).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 16, 0, 16)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return VH(row)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val cat = items[position]
        holder.row.removeAllViews()

        val nameText = TextView(holder.row.context).apply {
            text = "${cat.emoji} ${cat.name}"
            textSize = 16f
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        }

        val renameBtn = Button(holder.row.context).apply {
            text = "Rename"
            textSize = 12f
            setOnClickListener { onRename(cat) }
        }

        val deleteBtn = Button(holder.row.context).apply {
            text = "✕"
            textSize = 12f
            setOnClickListener { onDelete(cat) }
        }

        holder.row.addView(nameText)
        holder.row.addView(renameBtn)
        holder.row.addView(deleteBtn)
    }

    override fun getItemCount() = items.size
}
