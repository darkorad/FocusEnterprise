package com.focusenterprise.data;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PaymentDao_Impl implements PaymentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Payment> __insertionAdapterOfPayment;

  private final EntityDeletionOrUpdateAdapter<Payment> __deletionAdapterOfPayment;

  private final EntityDeletionOrUpdateAdapter<Payment> __updateAdapterOfPayment;

  private final SharedSQLiteStatement __preparedStmtOfDeletePaymentsForInvoice;

  public PaymentDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPayment = new EntityInsertionAdapter<Payment>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `payments` (`paymentId`,`invoiceId`,`amount`,`date`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Payment value) {
        stmt.bindLong(1, value.getPaymentId());
        stmt.bindLong(2, value.getInvoiceId());
        stmt.bindDouble(3, value.getAmount());
        stmt.bindLong(4, value.getDate());
      }
    };
    this.__deletionAdapterOfPayment = new EntityDeletionOrUpdateAdapter<Payment>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `payments` WHERE `paymentId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Payment value) {
        stmt.bindLong(1, value.getPaymentId());
      }
    };
    this.__updateAdapterOfPayment = new EntityDeletionOrUpdateAdapter<Payment>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `payments` SET `paymentId` = ?,`invoiceId` = ?,`amount` = ?,`date` = ? WHERE `paymentId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Payment value) {
        stmt.bindLong(1, value.getPaymentId());
        stmt.bindLong(2, value.getInvoiceId());
        stmt.bindDouble(3, value.getAmount());
        stmt.bindLong(4, value.getDate());
        stmt.bindLong(5, value.getPaymentId());
      }
    };
    this.__preparedStmtOfDeletePaymentsForInvoice = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM payments WHERE invoiceId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Payment payment) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPayment.insert(payment);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Payment payment) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPayment.handle(payment);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Payment payment) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPayment.handle(payment);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePaymentsForInvoice(final int invoiceId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePaymentsForInvoice.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, invoiceId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletePaymentsForInvoice.release(_stmt);
    }
  }

  @Override
  public Flow<List<Payment>> getAllPayments() {
    final String _sql = "SELECT `payments`.`paymentId` AS `paymentId`, `payments`.`invoiceId` AS `invoiceId`, `payments`.`amount` AS `amount`, `payments`.`date` AS `date` FROM payments ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"payments"}, new Callable<List<Payment>>() {
      @Override
      public List<Payment> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPaymentId = 0;
          final int _cursorIndexOfInvoiceId = 1;
          final int _cursorIndexOfAmount = 2;
          final int _cursorIndexOfDate = 3;
          final List<Payment> _result = new ArrayList<Payment>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Payment _item;
            final int _tmpPaymentId;
            _tmpPaymentId = _cursor.getInt(_cursorIndexOfPaymentId);
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            _item = new Payment(_tmpPaymentId,_tmpInvoiceId,_tmpAmount,_tmpDate);
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
  public Flow<List<Payment>> getPaymentsForInvoice(final int invoiceId) {
    final String _sql = "SELECT * FROM payments WHERE invoiceId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, invoiceId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"payments"}, new Callable<List<Payment>>() {
      @Override
      public List<Payment> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPaymentId = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentId");
          final int _cursorIndexOfInvoiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final List<Payment> _result = new ArrayList<Payment>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Payment _item;
            final int _tmpPaymentId;
            _tmpPaymentId = _cursor.getInt(_cursorIndexOfPaymentId);
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            _item = new Payment(_tmpPaymentId,_tmpInvoiceId,_tmpAmount,_tmpDate);
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
  public Flow<Double> getMonthlyPaymentsSum(final long startDate, final long endDate) {
    final String _sql = "SELECT COALESCE(SUM(amount), 0.0) FROM payments WHERE date BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"payments"}, new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if(_cursor.moveToFirst()) {
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
  public Flow<List<Payment>> getPaymentsForCustomer(final int customerId) {
    final String _sql = "SELECT * FROM payments WHERE invoiceId IN (SELECT invoiceId FROM invoices WHERE customerId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"payments","invoices"}, new Callable<List<Payment>>() {
      @Override
      public List<Payment> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPaymentId = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentId");
          final int _cursorIndexOfInvoiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final List<Payment> _result = new ArrayList<Payment>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Payment _item;
            final int _tmpPaymentId;
            _tmpPaymentId = _cursor.getInt(_cursorIndexOfPaymentId);
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            _item = new Payment(_tmpPaymentId,_tmpInvoiceId,_tmpAmount,_tmpDate);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
