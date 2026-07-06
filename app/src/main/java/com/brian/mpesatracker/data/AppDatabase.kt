package com.brian.mpesatracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Transaction::class, Category::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        // Migration: add txnType column + categories table
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE transactions ADD COLUMN txnType TEXT NOT NULL DEFAULT 'OTHER'")
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS categories (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        emoji TEXT NOT NULL DEFAULT '',
                        isDefault INTEGER NOT NULL DEFAULT 0
                    )
                """)
            }
        }

        // Migration: add provider column (MPESA / AIRTEL) so Airtel Money
        // transactions can be tracked alongside M-PESA ones
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE transactions ADD COLUMN provider TEXT NOT NULL DEFAULT 'MPESA'")
            }
        }

        private val DEFAULT_CATEGORIES = listOf(
            Category(name = "Fare",           emoji = "🚌", isDefault = true),
            Category(name = "Groceries",      emoji = "🛒", isDefault = true),
            Category(name = "Food",           emoji = "🍽️", isDefault = true),
            Category(name = "Toiletries",     emoji = "🧴", isDefault = true),
            Category(name = "Weed",           emoji = "🌿", isDefault = true),
            Category(name = "Alcohol",        emoji = "🍺", isDefault = true),
            Category(name = "Bills",          emoji = "⚡", isDefault = true),
            Category(name = "Transport",      emoji = "🚗", isDefault = true),
            Category(name = "Savings",        emoji = "💰", isDefault = true),
            Category(name = "Business",       emoji = "💼", isDefault = true),
            Category(name = "Family/Personal",emoji = "👨‍👩‍👧", isDefault = true),
            Category(name = "Airtime/Data",   emoji = "📱", isDefault = true),
            Category(name = "Other",          emoji = "📦", isDefault = true)
        )

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mpesa_tracker.db"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Seed categories on fresh install
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    val dao = database.categoryDao()
                                    DEFAULT_CATEGORIES.forEach { dao.insert(it) }
                                }
                            }
                        }
                    })
                    .build()
                    .also { db ->
                        INSTANCE = db
                        // Also seed if categories table is empty (e.g. after migration)
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = db.categoryDao()
                            if (dao.count() == 0) {
                                DEFAULT_CATEGORIES.forEach { dao.insert(it) }
                            }
                        }
                    }
            }
        }
    }
}
