package com.brian.mpesatracker

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brian.mpesatracker.data.AppDatabase
import com.brian.mpesatracker.data.CategoryTotal
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import java.util.Calendar
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TransactionAdapter

    private lateinit var dashboardScroll: ScrollView
    private lateinit var historyPanel: LinearLayout

    // Dashboard stat rows
    private lateinit var totalSpendText: TextView
    private lateinit var incomeRow: LinearLayout
    private lateinit var totalIncomeText: TextView
    private lateinit var feesRow: LinearLayout
    private lateinit var totalFeesText: TextView
    private lateinit var fulizaRow: LinearLayout
    private lateinit var totalFulizaText: TextView
    private lateinit var uncategorizedBadge: TextView
    private lateinit var categoryBarChart: BarChartView
    private lateinit var categoryBreakdownText: TextView

    // History views
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyText: TextView

    private var currentPeriodStart: Long = weekStart()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionsIfNeeded()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val content = FrameLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
            )
        }

        dashboardScroll = buildDashboard()
        content.addView(dashboardScroll)

        historyPanel = buildHistory()
        historyPanel.visibility = View.GONE
        content.addView(historyPanel)

        val bottomNav = BottomNavigationView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            inflateMenu(R.menu.bottom_nav)
        }

        root.addView(content)
        root.addView(bottomNav)
        setContentView(root)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    dashboardScroll.visibility = View.VISIBLE
                    historyPanel.visibility = View.GONE
                    true
                }
                R.id.nav_history -> {
                    dashboardScroll.visibility = View.GONE
                    historyPanel.visibility = View.VISIBLE
                    true
                }
                R.id.nav_categories -> {
                    startActivity(Intent(this, ManageCategoriesActivity::class.java))
                    false
                }
                else -> false
            }
        }

        observeData()
    }

    // ── Dashboard ────────────────────────────────────────────────────────────
    private fun buildDashboard(): ScrollView {
        val scroll = ScrollView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }

        val panel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 40, 48, 40)
        }

        // App name header
        val appName = TextView(this).apply {
            text = "Pesa Track"
            textSize = 26f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(Color.parseColor("#1A1A1A"))
        }
        val tagline = TextView(this).apply {
            text = "Know where your money goes."
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            setPadding(0, 2, 0, 24)
        }

        // Period toggle
        val periodToggle = RadioGroup(this).apply {
            orientation = RadioGroup.HORIZONTAL
            setPadding(0, 0, 0, 24)
        }
        listOf("Week" to "week", "Month" to "month", "Year" to "year").forEach { (label, key) ->
            val rb = RadioButton(this).apply {
                text = label
                tag = key
                if (key == "week") isChecked = true
                textSize = 14f
            }
            periodToggle.addView(rb)
        }
        periodToggle.setOnCheckedChangeListener { group, checkedId ->
            val rb = group.findViewById<RadioButton>(checkedId)
            currentPeriodStart = when (rb?.tag) {
                "month" -> monthStart()
                "year"  -> yearStart()
                else    -> weekStart()
            }
            observeData()
        }

        // Uncategorized badge
        uncategorizedBadge = TextView(this).apply {
            textSize = 12f
            setTextColor(Color.parseColor("#E65100"))
            setPadding(0, 0, 0, 12)
            visibility = View.GONE
        }

        // Spend — always shown
        totalSpendText = TextView(this).apply {
            textSize = 32f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(Color.parseColor("#C62828"))
            setPadding(0, 0, 0, 4)
        }
        val spendLabel = TextView(this).apply {
            text = "total spent"
            textSize = 12f
            setTextColor(Color.parseColor("#AAAAAA"))
            setPadding(0, 0, 0, 20)
        }

        // Income row — hidden when 0
        incomeRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 0, 0, 8)
            visibility = View.GONE
        }
        val incomeLabel = TextView(this).apply {
            text = "Received  "
            textSize = 14f
            setTextColor(Color.parseColor("#555555"))
        }
        totalIncomeText = TextView(this).apply {
            textSize = 14f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(Color.parseColor("#2E7D32"))
        }
        incomeRow.addView(incomeLabel)
        incomeRow.addView(totalIncomeText)

        // Fees row — hidden when 0
        feesRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 0, 0, 8)
            visibility = View.GONE
        }
        val feesLabel = TextView(this).apply {
            text = "Fees paid  "
            textSize = 14f
            setTextColor(Color.parseColor("#555555"))
        }
        totalFeesText = TextView(this).apply {
            textSize = 14f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(Color.parseColor("#555555"))
        }
        feesRow.addView(feesLabel)
        feesRow.addView(totalFeesText)

        // Fuliza row — hidden when 0
        fulizaRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 0, 0, 8)
            visibility = View.GONE
        }
        val fulizaLabel = TextView(this).apply {
            text = "Fuliza used  "
            textSize = 14f
            setTextColor(Color.parseColor("#555555"))
        }
        totalFulizaText = TextView(this).apply {
            textSize = 14f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(Color.parseColor("#B71C1C"))
        }
        fulizaRow.addView(fulizaLabel)
        fulizaRow.addView(totalFulizaText)

        // Divider
        val divider = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1
            ).apply { setMargins(0, 20, 0, 20) }
            setBackgroundColor(Color.parseColor("#E0E0E0"))
        }

        // Category section header
        val catHeader = TextView(this).apply {
            text = "Spend by category"
            textSize = 15f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(Color.parseColor("#1A1A1A"))
            setPadding(0, 0, 0, 16)
        }

        // Bar chart
        categoryBarChart = BarChartView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0
            )
            visibility = View.GONE
        }

        // Fallback text
        categoryBreakdownText = TextView(this).apply {
            textSize = 14f
            setTextColor(Color.parseColor("#AAAAAA"))
            text = "No categorized spend yet"
        }

        panel.addView(appName)
        panel.addView(tagline)
        panel.addView(periodToggle)
        panel.addView(uncategorizedBadge)
        panel.addView(totalSpendText)
        panel.addView(spendLabel)
        panel.addView(incomeRow)
        panel.addView(feesRow)
        panel.addView(fulizaRow)
        panel.addView(divider)
        panel.addView(catHeader)
        panel.addView(categoryBarChart)
        panel.addView(categoryBreakdownText)

        scroll.addView(panel)
        return scroll
    }

    // ── History ──────────────────────────────────────────────────────────────
    private fun buildHistory(): LinearLayout {
        val panel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 0)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }

        val header = TextView(this).apply {
            text = "All Transactions"
            textSize = 18f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setPadding(8, 8, 8, 12)
        }

        emptyText = TextView(this).apply {
            text = "No M-Pesa messages picked up yet."
            setPadding(8, 8, 8, 8)
            setTextColor(Color.parseColor("#AAAAAA"))
            visibility = View.GONE
        }

        recyclerView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
            )
        }
        adapter = TransactionAdapter { txnId ->
            val intent = Intent(this, CategorizeActivity::class.java)
            intent.putExtra("txn_id", txnId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        panel.addView(header)
        panel.addView(emptyText)
        panel.addView(recyclerView)
        return panel
    }

    // ── Observe LiveData ─────────────────────────────────────────────────────
    private fun observeData() {
        val dao = AppDatabase.getInstance(this).transactionDao()
        val from = currentPeriodStart

        dao.getAll().observe(this) { list ->
            adapter.submitList(list)
            emptyText.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        dao.getTotalSpend(from).observe(this) { total ->
            totalSpendText.text = "Ksh ${fmt(total)}"
        }

        dao.getTotalIncome(from).observe(this) { total ->
            val amt = total ?: 0.0
            if (amt > 0) {
                totalIncomeText.text = "Ksh ${fmt(total)}"
                incomeRow.visibility = View.VISIBLE
            } else {
                incomeRow.visibility = View.GONE
            }
        }

        dao.getTotalFees(from).observe(this) { total ->
            val amt = total ?: 0.0
            if (amt > 0) {
                totalFeesText.text = "Ksh ${fmt(total)}"
                feesRow.visibility = View.VISIBLE
            } else {
                feesRow.visibility = View.GONE
            }
        }

        dao.getTotalFuliza(from).observe(this) { total ->
            val amt = total ?: 0.0
            if (amt > 0) {
                totalFulizaText.text = "Ksh ${fmt(total)}"
                fulizaRow.visibility = View.VISIBLE
            } else {
                fulizaRow.visibility = View.GONE
            }
        }

        dao.getUncategorizedCount().observe(this) { count ->
            if (count > 0) {
                uncategorizedBadge.text =
                    "⚠ $count transaction${if (count == 1) "" else "s"} uncategorized"
                uncategorizedBadge.visibility = View.VISIBLE
            } else {
                uncategorizedBadge.visibility = View.GONE
            }
        }

        dao.getSpendByCategory(from).observe(this) { breakdown ->
            if (breakdown.isEmpty()) {
                categoryBarChart.visibility = View.GONE
                categoryBreakdownText.visibility = View.VISIBLE
                categoryBreakdownText.text = "No categorized spend yet"
            } else {
                categoryBreakdownText.visibility = View.GONE
                categoryBarChart.visibility = View.VISIBLE
                categoryBarChart.setData(breakdown.sortedByDescending { it.total })
            }
        }
    }

    private fun fmt(d: Double?) = String.format("%,.2f", d ?: 0.0)

    private fun weekStart() = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
        set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    private fun monthStart() = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    private fun yearStart() = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_YEAR, 1)
        set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    private fun requestPermissionsIfNeeded() {
        val needed = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
            != PackageManager.PERMISSION_GRANTED) needed.add(Manifest.permission.RECEIVE_SMS)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
            != PackageManager.PERMISSION_GRANTED) needed.add(Manifest.permission.READ_SMS)
        if (Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) needed.add(Manifest.permission.POST_NOTIFICATIONS)
        if (needed.isNotEmpty()) permissionLauncher.launch(needed.toTypedArray())
    }
}

// ── Custom bar chart view ────────────────────────────────────────────────────
class BarChartView(context: android.content.Context) : android.view.View(context) {

    private var data: List<CategoryTotal> = emptyList()
    private val barColors = listOf(
        "#1A73E8", "#E53935", "#43A047", "#FB8C00", "#8E24AA",
        "#00ACC1", "#6D4C41", "#F4511E", "#3949AB", "#00897B",
        "#C0CA33", "#FFB300", "#757575"
    )

    // Convert all fixed sizes from dp to px so the chart looks the same on every device.
    private val d = context.resources.displayMetrics.density
    private val rowH = 40f * d
    private val gap = 12f * d
    private val cornerR = 6f * d
    private val labelAmtGap = 8f * d   // breathing room between bar end and amount text

    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = android.text.TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 13f * d
        color = Color.parseColor("#1A1A1A")
    }
    private val amtPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12.5f * d
        color = Color.parseColor("#555555")
        textAlign = Paint.Align.RIGHT   // draw right-to-left from a fixed right edge
    }
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#F0F0F0")
    }

    // Computed once per data set so columns always fit their content.
    private var labelW = 0f
    private var amtColW = 0f

    fun setData(items: List<CategoryTotal>) {
        data = items

        // Size the label column to the widest category name (capped so bars keep room to breathe).
        val maxLabelW = items.maxOfOrNull { textPaint.measureText(it.category) } ?: 0f
        labelW = (maxLabelW + 16f * d).coerceIn(56f * d, 120f * d)

        // Size the amount column to the widest formatted amount actually being shown.
        val maxAmtW = items.maxOfOrNull { amtPaint.measureText(formatAmt(it.total)) } ?: 0f
        amtColW = maxAmtW + 4f * d

        val totalHeight = (items.size * (rowH + gap) + 8f * d).toInt()
        layoutParams = layoutParams?.apply { height = totalHeight }
            ?: LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, totalHeight
            )
        invalidate()
        requestLayout()
    }

    private fun formatAmt(v: Double) = "Ksh ${String.format("%,.0f", v)}"

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (data.isEmpty() || width == 0) return

        val maxVal = data.maxOf { it.total }
        val amtRightEdge = width.toFloat()
        val barEndX = amtRightEdge - amtColW - labelAmtGap
        val availW = (barEndX - labelW).coerceAtLeast(4f * d)

        data.forEachIndexed { i, item ->
            val top = i * (rowH + gap) + 4f * d
            val barW = ((item.total / maxVal) * availW).toFloat().coerceAtLeast(4f * d)

            // Background track
            canvas.drawRoundRect(
                RectF(labelW, top, labelW + availW, top + rowH),
                cornerR, cornerR, bgPaint
            )

            // Colored bar
            barPaint.color = Color.parseColor(barColors[i % barColors.size])
            canvas.drawRoundRect(
                RectF(labelW, top, labelW + barW, top + rowH),
                cornerR, cornerR, barPaint
            )

            val textY = top + rowH / 2f + textPaint.textSize / 3f

            // Category label — ellipsize instead of overflowing into the bar
            val label = android.text.TextUtils.ellipsize(
                item.category, textPaint, labelW - 8f * d, android.text.TextUtils.TruncateAt.END
            ).toString()
            canvas.drawText(label, 0f, textY, textPaint)

            // Amount, right-aligned to the view's right edge — never clipped
            canvas.drawText(formatAmt(item.total), amtRightEdge, textY, amtPaint)
        }
    }
}