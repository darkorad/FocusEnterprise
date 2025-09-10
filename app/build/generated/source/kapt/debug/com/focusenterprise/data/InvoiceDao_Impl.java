package com.focusenterprise.data;

import android.database.Cursor;
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
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class InvoiceDao_Impl implements InvoiceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Invoice> __insertionAdapterOfInvoice;

  private final EntityDeletionOrUpdateAdapter<Invoice> __deletionAdapterOfInvoice;

  private final EntityDeletionOrUpdateAdapter<Invoice> __updateAdapterOfInvoice;

  public InvoiceDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInvoice = new EntityInsertionAdapter<Invoice>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `invoices` (`invoiceId`,`customerId`,`date`,`totalAmount`,`paidAmount`,`status`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Invoice value) {
        stmt.bindLong(1, value.getInvoiceId());
        stmt.bindLong(2, value.getCustomerId());
        stmt.bindLong(3, value.getDate());
        stmt.bindDouble(4, value.getTotalAmount());
        stmt.bindDouble(5, value.getPaidAmount());
        if (value.getStatus() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStatus());
        }
      }
    };
    this.__deletionAdapterOfInvoice = new EntityDeletionOrUpdateAdapter<Invoice>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `invoices` WHERE `invoiceId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Invoice value) {
        stmt.bindLong(1, value.getInvoiceId());
      }
    };
    this.__updateAdapterOfInvoice = new EntityDeletionOrUpdateAdapter<Invoice>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `invoices` SET `invoiceId` = ?,`customerId` = ?,`date` = ?,`totalAmount` = ?,`paidAmount` = ?,`status` = ? WHERE `invoiceId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Invoice value) {
        stmt.bindLong(1, value.getInvoiceId());
        stmt.bindLong(2, value.getCustomerId());
        stmt.bindLong(3, value.getDate());
        stmt.bindDouble(4, value.getTotalAmount());
        stmt.bindDouble(5, value.getPaidAmount());
        if (value.getStatus() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getStatus());
        }
        stmt.bindLong(7, value.getInvoiceId());
      }
    };
  }

  @Override
  public long insert(final Invoice invoice) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfInvoice.insertAndReturnId(invoice);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Invoice invoice) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfInvoice.handle(invoice);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Invoice invoice) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfInvoice.handle(invoice);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Flow<List<Invoice>> getAllInvoices() {
    final String _sql = "SELECT `invoices`.`invoiceId` AS `invoiceId`, `invoices`.`customerId` AS `customerId`, `invoices`.`date` AS `date`, `invoices`.`totalAmount` AS `totalAmount`, `invoices`.`paidAmount` AS `paidAmount`, `invoices`.`status` AS `status` FROM invoices ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoices"}, new Callable<List<Invoice>>() {
      @Override
      public List<Invoice> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInvoiceId = 0;
          final int _cursorIndexOfCustomerId = 1;
          final int _cursorIndexOfDate = 2;
          final int _cursorIndexOfTotalAmount = 3;
          final int _cursorIndexOfPaidAmount = 4;
          final int _cursorIndexOfStatus = 5;
          final List<Invoice> _result = new ArrayList<Invoice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Invoice _item;
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final double _tmpPaidAmount;
            _tmpPaidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item = new Invoice(_tmpInvoiceId,_tmpCustomerId,_tmpDate,_tmpTotalAmount,_tmpPaidAmount,_tmpStatus);
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
  public Flow<List<Invoice>> getInvoicesByDateRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM invoices WHERE date BETWEEN ? AND ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoices"}, new Callable<List<Invoice>>() {
      @Override
      public List<Invoice> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInvoiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceId");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfPaidAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<Invoice> _result = new ArrayList<Invoice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Invoice _item;
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final double _tmpPaidAmount;
            _tmpPaidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item = new Invoice(_tmpInvoiceId,_tmpCustomerId,_tmpDate,_tmpTotalAmount,_tmpPaidAmount,_tmpStatus);
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
  public Flow<Double> getMonthlySalesSum(final long startDate, final long endDate) {
    final String _sql = "SELECT COALESCE(SUM(totalAmount), 0.0) FROM invoices WHERE date BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoices"}, new Callable<Double>() {
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
  public Flow<List<Invoice>> getInvoicesForCustomer(final int customerId) {
    final String _sql = "SELECT * FROM invoices WHERE customerId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoices"}, new Callable<List<Invoice>>() {
      @Override
      public List<Invoice> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInvoiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceId");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfPaidAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<Invoice> _result = new ArrayList<Invoice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Invoice _item;
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final double _tmpPaidAmount;
            _tmpPaidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item = new Invoice(_tmpInvoiceId,_tmpCustomerId,_tmpDate,_tmpTotalAmount,_tmpPaidAmount,_tmpStatus);
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
  public Flow<List<Invoice>> getActiveInvoices() {
    final String _sql = "SELECT `invoices`.`invoiceId` AS `invoiceId`, `invoices`.`customerId` AS `customerId`, `invoices`.`date` AS `date`, `invoices`.`totalAmount` AS `totalAmount`, `invoices`.`paidAmount` AS `paidAmount`, `invoices`.`status` AS `status` FROM invoices WHERE status != 'paid' ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoices"}, new Callable<List<Invoice>>() {
      @Override
      public List<Invoice> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInvoiceId = 0;
          final int _cursorIndexOfCustomerId = 1;
          final int _cursorIndexOfDate = 2;
          final int _cursorIndexOfTotalAmount = 3;
          final int _cursorIndexOfPaidAmount = 4;
          final int _cursorIndexOfStatus = 5;
          final List<Invoice> _result = new ArrayList<Invoice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Invoice _item;
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final double _tmpPaidAmount;
            _tmpPaidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item = new Invoice(_tmpInvoiceId,_tmpCustomerId,_tmpDate,_tmpTotalAmount,_tmpPaidAmount,_tmpStatus);
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
  public Flow<List<Invoice>> getCompletedInvoices() {
    final String _sql = "SELECT `invoices`.`invoiceId` AS `invoiceId`, `invoices`.`customerId` AS `customerId`, `invoices`.`date` AS `date`, `invoices`.`totalAmount` AS `totalAmount`, `invoices`.`paidAmount` AS `paidAmount`, `invoices`.`status` AS `status` FROM invoices WHERE status = 'paid' ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoices"}, new Callable<List<Invoice>>() {
      @Override
      public List<Invoice> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInvoiceId = 0;
          final int _cursorIndexOfCustomerId = 1;
          final int _cursorIndexOfDate = 2;
          final int _cursorIndexOfTotalAmount = 3;
          final int _cursorIndexOfPaidAmount = 4;
          final int _cursorIndexOfStatus = 5;
          final List<Invoice> _result = new ArrayList<Invoice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Invoice _item;
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final double _tmpPaidAmount;
            _tmpPaidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item = new Invoice(_tmpInvoiceId,_tmpCustomerId,_tmpDate,_tmpTotalAmount,_tmpPaidAmount,_tmpStatus);
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
  public Flow<List<Invoice>> getActiveInvoicesForCustomer(final int customerId) {
    final String _sql = "SELECT * FROM invoices WHERE customerId = ? AND status != 'paid' ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoices"}, new Callable<List<Invoice>>() {
      @Override
      public List<Invoice> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInvoiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceId");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfPaidAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<Invoice> _result = new ArrayList<Invoice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Invoice _item;
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final double _tmpPaidAmount;
            _tmpPaidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item = new Invoice(_tmpInvoiceId,_tmpCustomerId,_tmpDate,_tmpTotalAmount,_tmpPaidAmount,_tmpStatus);
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
  public Flow<List<Invoice>> getCompletedInvoicesForCustomer(final int customerId) {
    final String _sql = "SELECT * FROM invoices WHERE customerId = ? AND status = 'paid' ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoices"}, new Callable<List<Invoice>>() {
      @Override
      public List<Invoice> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfInvoiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceId");
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAmount");
          final int _cursorIndexOfPaidAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "paidAmount");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final List<Invoice> _result = new ArrayList<Invoice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Invoice _item;
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final double _tmpPaidAmount;
            _tmpPaidAmount = _cursor.getDouble(_cursorIndexOfPaidAmount);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item = new Invoice(_tmpInvoiceId,_tmpCustomerId,_tmpDate,_tmpTotalAmount,_tmpPaidAmount,_tmpStatus);
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
  public Flow<List<CustomerMonthlySummary>> getCustomerMonthlySummaries() {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            i.customerId,\n"
            + "            c.name as customerName,\n"
            + "            strftime('%Y-%m', datetime(i.date/1000, 'unixepoch')) as monthYear,\n"
            + "            COALESCE(SUM(i.totalAmount), 0.0) as totalInvoiceAmount,\n"
            + "            COALESCE(SUM(i.paidAmount), 0.0) as totalPaidAmount,\n"
            + "            COUNT(i.invoiceId) as invoiceCount\n"
            + "        FROM invoices i\n"
            + "        INNER JOIN customers c ON i.customerId = c.customerId\n"
            + "        GROUP BY i.customerId, strftime('%Y-%m', datetime(i.date/1000, 'unixepoch'))\n"
            + "        ORDER BY monthYear DESC, c.name ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoices","customers"}, new Callable<List<CustomerMonthlySummary>>() {
      @Override
      public List<CustomerMonthlySummary> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCustomerId = 0;
          final int _cursorIndexOfCustomerName = 1;
          final int _cursorIndexOfMonthYear = 2;
          final int _cursorIndexOfTotalInvoiceAmount = 3;
          final int _cursorIndexOfTotalPaidAmount = 4;
          final int _cursorIndexOfInvoiceCount = 5;
          final List<CustomerMonthlySummary> _result = new ArrayList<CustomerMonthlySummary>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CustomerMonthlySummary _item;
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final String _tmpCustomerName;
            if (_cursor.isNull(_cursorIndexOfCustomerName)) {
              _tmpCustomerName = null;
            } else {
              _tmpCustomerName = _cursor.getString(_cursorIndexOfCustomerName);
            }
            final String _tmpMonthYear;
            if (_cursor.isNull(_cursorIndexOfMonthYear)) {
              _tmpMonthYear = null;
            } else {
              _tmpMonthYear = _cursor.getString(_cursorIndexOfMonthYear);
            }
            final double _tmpTotalInvoiceAmount;
            _tmpTotalInvoiceAmount = _cursor.getDouble(_cursorIndexOfTotalInvoiceAmount);
            final double _tmpTotalPaidAmount;
            _tmpTotalPaidAmount = _cursor.getDouble(_cursorIndexOfTotalPaidAmount);
            final int _tmpInvoiceCount;
            _tmpInvoiceCount = _cursor.getInt(_cursorIndexOfInvoiceCount);
            _item = new CustomerMonthlySummary(_tmpCustomerId,_tmpCustomerName,_tmpMonthYear,_tmpTotalInvoiceAmount,_tmpTotalPaidAmount,_tmpInvoiceCount);
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
