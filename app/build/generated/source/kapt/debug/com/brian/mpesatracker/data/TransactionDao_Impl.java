package com.brian.mpesatracker.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Transaction> __insertionAdapterOfTransaction;

  private final EntityDeletionOrUpdateAdapter<Transaction> __updateAdapterOfTransaction;

  public TransactionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransaction = new EntityInsertionAdapter<Transaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `transactions` (`id`,`txnCode`,`amount`,`counterparty`,`direction`,`txnType`,`category`,`balance`,`cost`,`rawMessage`,`timestamp`,`provider`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Transaction entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTxnCode() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTxnCode());
        }
        statement.bindDouble(3, entity.getAmount());
        if (entity.getCounterparty() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCounterparty());
        }
        if (entity.getDirection() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDirection());
        }
        if (entity.getTxnType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTxnType());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCategory());
        }
        if (entity.getBalance() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getBalance());
        }
        statement.bindDouble(9, entity.getCost());
        if (entity.getRawMessage() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getRawMessage());
        }
        statement.bindLong(11, entity.getTimestamp());
        if (entity.getProvider() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getProvider());
        }
      }
    };
    this.__updateAdapterOfTransaction = new EntityDeletionOrUpdateAdapter<Transaction>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `transactions` SET `id` = ?,`txnCode` = ?,`amount` = ?,`counterparty` = ?,`direction` = ?,`txnType` = ?,`category` = ?,`balance` = ?,`cost` = ?,`rawMessage` = ?,`timestamp` = ?,`provider` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Transaction entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTxnCode() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTxnCode());
        }
        statement.bindDouble(3, entity.getAmount());
        if (entity.getCounterparty() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCounterparty());
        }
        if (entity.getDirection() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDirection());
        }
        if (entity.getTxnType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTxnType());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCategory());
        }
        if (entity.getBalance() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getBalance());
        }
        statement.bindDouble(9, entity.getCost());
        if (entity.getRawMessage() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getRawMessage());
        }
        statement.bindLong(11, entity.getTimestamp());
        if (entity.getProvider() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getProvider());
        }
        statement.bindLong(13, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final Transaction transaction,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTransaction.insertAndReturnId(transaction);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Transaction transaction,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTransaction.handle(transaction);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Transaction>> getAll() {
    final String _sql = "SELECT * FROM transactions ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<Transaction>>() {
      @Override
      @Nullable
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTxnCode = CursorUtil.getColumnIndexOrThrow(_cursor, "txnCode");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCounterparty = CursorUtil.getColumnIndexOrThrow(_cursor, "counterparty");
          final int _cursorIndexOfDirection = CursorUtil.getColumnIndexOrThrow(_cursor, "direction");
          final int _cursorIndexOfTxnType = CursorUtil.getColumnIndexOrThrow(_cursor, "txnType");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfRawMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "rawMessage");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProvider = CursorUtil.getColumnIndexOrThrow(_cursor, "provider");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTxnCode;
            if (_cursor.isNull(_cursorIndexOfTxnCode)) {
              _tmpTxnCode = null;
            } else {
              _tmpTxnCode = _cursor.getString(_cursorIndexOfTxnCode);
            }
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpCounterparty;
            if (_cursor.isNull(_cursorIndexOfCounterparty)) {
              _tmpCounterparty = null;
            } else {
              _tmpCounterparty = _cursor.getString(_cursorIndexOfCounterparty);
            }
            final String _tmpDirection;
            if (_cursor.isNull(_cursorIndexOfDirection)) {
              _tmpDirection = null;
            } else {
              _tmpDirection = _cursor.getString(_cursorIndexOfDirection);
            }
            final String _tmpTxnType;
            if (_cursor.isNull(_cursorIndexOfTxnType)) {
              _tmpTxnType = null;
            } else {
              _tmpTxnType = _cursor.getString(_cursorIndexOfTxnType);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final Double _tmpBalance;
            if (_cursor.isNull(_cursorIndexOfBalance)) {
              _tmpBalance = null;
            } else {
              _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            }
            final double _tmpCost;
            _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
            final String _tmpRawMessage;
            if (_cursor.isNull(_cursorIndexOfRawMessage)) {
              _tmpRawMessage = null;
            } else {
              _tmpRawMessage = _cursor.getString(_cursorIndexOfRawMessage);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProvider;
            if (_cursor.isNull(_cursorIndexOfProvider)) {
              _tmpProvider = null;
            } else {
              _tmpProvider = _cursor.getString(_cursorIndexOfProvider);
            }
            _item = new Transaction(_tmpId,_tmpTxnCode,_tmpAmount,_tmpCounterparty,_tmpDirection,_tmpTxnType,_tmpCategory,_tmpBalance,_tmpCost,_tmpRawMessage,_tmpTimestamp,_tmpProvider);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Transaction>> getAllSince(final long from) {
    final String _sql = "SELECT * FROM transactions WHERE timestamp >= ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, from);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<Transaction>>() {
      @Override
      @Nullable
      public List<Transaction> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTxnCode = CursorUtil.getColumnIndexOrThrow(_cursor, "txnCode");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCounterparty = CursorUtil.getColumnIndexOrThrow(_cursor, "counterparty");
          final int _cursorIndexOfDirection = CursorUtil.getColumnIndexOrThrow(_cursor, "direction");
          final int _cursorIndexOfTxnType = CursorUtil.getColumnIndexOrThrow(_cursor, "txnType");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfRawMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "rawMessage");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProvider = CursorUtil.getColumnIndexOrThrow(_cursor, "provider");
          final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Transaction _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTxnCode;
            if (_cursor.isNull(_cursorIndexOfTxnCode)) {
              _tmpTxnCode = null;
            } else {
              _tmpTxnCode = _cursor.getString(_cursorIndexOfTxnCode);
            }
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpCounterparty;
            if (_cursor.isNull(_cursorIndexOfCounterparty)) {
              _tmpCounterparty = null;
            } else {
              _tmpCounterparty = _cursor.getString(_cursorIndexOfCounterparty);
            }
            final String _tmpDirection;
            if (_cursor.isNull(_cursorIndexOfDirection)) {
              _tmpDirection = null;
            } else {
              _tmpDirection = _cursor.getString(_cursorIndexOfDirection);
            }
            final String _tmpTxnType;
            if (_cursor.isNull(_cursorIndexOfTxnType)) {
              _tmpTxnType = null;
            } else {
              _tmpTxnType = _cursor.getString(_cursorIndexOfTxnType);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final Double _tmpBalance;
            if (_cursor.isNull(_cursorIndexOfBalance)) {
              _tmpBalance = null;
            } else {
              _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            }
            final double _tmpCost;
            _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
            final String _tmpRawMessage;
            if (_cursor.isNull(_cursorIndexOfRawMessage)) {
              _tmpRawMessage = null;
            } else {
              _tmpRawMessage = _cursor.getString(_cursorIndexOfRawMessage);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProvider;
            if (_cursor.isNull(_cursorIndexOfProvider)) {
              _tmpProvider = null;
            } else {
              _tmpProvider = _cursor.getString(_cursorIndexOfProvider);
            }
            _item = new Transaction(_tmpId,_tmpTxnCode,_tmpAmount,_tmpCounterparty,_tmpDirection,_tmpTxnType,_tmpCategory,_tmpBalance,_tmpCost,_tmpRawMessage,_tmpTimestamp,_tmpProvider);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getById(final long id, final Continuation<? super Transaction> $completion) {
    final String _sql = "SELECT * FROM transactions WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Transaction>() {
      @Override
      @Nullable
      public Transaction call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTxnCode = CursorUtil.getColumnIndexOrThrow(_cursor, "txnCode");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCounterparty = CursorUtil.getColumnIndexOrThrow(_cursor, "counterparty");
          final int _cursorIndexOfDirection = CursorUtil.getColumnIndexOrThrow(_cursor, "direction");
          final int _cursorIndexOfTxnType = CursorUtil.getColumnIndexOrThrow(_cursor, "txnType");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfRawMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "rawMessage");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfProvider = CursorUtil.getColumnIndexOrThrow(_cursor, "provider");
          final Transaction _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTxnCode;
            if (_cursor.isNull(_cursorIndexOfTxnCode)) {
              _tmpTxnCode = null;
            } else {
              _tmpTxnCode = _cursor.getString(_cursorIndexOfTxnCode);
            }
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpCounterparty;
            if (_cursor.isNull(_cursorIndexOfCounterparty)) {
              _tmpCounterparty = null;
            } else {
              _tmpCounterparty = _cursor.getString(_cursorIndexOfCounterparty);
            }
            final String _tmpDirection;
            if (_cursor.isNull(_cursorIndexOfDirection)) {
              _tmpDirection = null;
            } else {
              _tmpDirection = _cursor.getString(_cursorIndexOfDirection);
            }
            final String _tmpTxnType;
            if (_cursor.isNull(_cursorIndexOfTxnType)) {
              _tmpTxnType = null;
            } else {
              _tmpTxnType = _cursor.getString(_cursorIndexOfTxnType);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final Double _tmpBalance;
            if (_cursor.isNull(_cursorIndexOfBalance)) {
              _tmpBalance = null;
            } else {
              _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            }
            final double _tmpCost;
            _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
            final String _tmpRawMessage;
            if (_cursor.isNull(_cursorIndexOfRawMessage)) {
              _tmpRawMessage = null;
            } else {
              _tmpRawMessage = _cursor.getString(_cursorIndexOfRawMessage);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpProvider;
            if (_cursor.isNull(_cursorIndexOfProvider)) {
              _tmpProvider = null;
            } else {
              _tmpProvider = _cursor.getString(_cursorIndexOfProvider);
            }
            _result = new Transaction(_tmpId,_tmpTxnCode,_tmpAmount,_tmpCounterparty,_tmpDirection,_tmpTxnType,_tmpCategory,_tmpBalance,_tmpCost,_tmpRawMessage,_tmpTimestamp,_tmpProvider);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<CategoryTotal>> getSpendByCategory(final long from) {
    final String _sql = "\n"
            + "        SELECT category, SUM(amount) as total \n"
            + "        FROM transactions \n"
            + "        WHERE direction = 'EXPENSE' AND category IS NOT NULL AND timestamp >= ?\n"
            + "        GROUP BY category\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, from);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<List<CategoryTotal>>() {
      @Override
      @Nullable
      public List<CategoryTotal> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategory = 0;
          final int _cursorIndexOfTotal = 1;
          final List<CategoryTotal> _result = new ArrayList<CategoryTotal>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryTotal _item;
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            _item = new CategoryTotal(_tmpCategory,_tmpTotal);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getTotalSpend(final long from) {
    final String _sql = "SELECT SUM(amount) FROM transactions WHERE direction = 'EXPENSE' AND timestamp >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, from);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getTotalIncome(final long from) {
    final String _sql = "SELECT SUM(amount) FROM transactions WHERE direction = 'INCOME' AND timestamp >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, from);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getTotalFees(final long from) {
    final String _sql = "SELECT SUM(cost) FROM transactions WHERE timestamp >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, from);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Integer> getUncategorizedCount() {
    final String _sql = "SELECT COUNT(*) FROM transactions WHERE direction = 'EXPENSE' AND category IS NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLastCategoryForCounterparty(final String counterparty,
      final Continuation<? super String> $completion) {
    final String _sql = "\n"
            + "        SELECT category FROM transactions \n"
            + "        WHERE counterparty = ? AND category IS NOT NULL \n"
            + "        ORDER BY timestamp DESC LIMIT 1\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (counterparty == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, counterparty);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<String>() {
      @Override
      @Nullable
      public String call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final String _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getString(0);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<Double> getTotalFuliza(final long from) {
    final String _sql = "SELECT SUM(amount) FROM transactions WHERE txnType = 'FULIZA' AND timestamp >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, from);
    return __db.getInvalidationTracker().createLiveData(new String[] {"transactions"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
