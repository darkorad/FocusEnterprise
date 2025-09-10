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
public final class InvoiceItemDao_Impl implements InvoiceItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<InvoiceItem> __insertionAdapterOfInvoiceItem;

  private final EntityDeletionOrUpdateAdapter<InvoiceItem> __deletionAdapterOfInvoiceItem;

  private final EntityDeletionOrUpdateAdapter<InvoiceItem> __updateAdapterOfInvoiceItem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteItemsForInvoice;

  public InvoiceItemDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInvoiceItem = new EntityInsertionAdapter<InvoiceItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `invoice_items` (`itemId`,`invoiceId`,`stockId`,`quantity`,`unitPrice`,`totalPrice`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, InvoiceItem value) {
        stmt.bindLong(1, value.getItemId());
        stmt.bindLong(2, value.getInvoiceId());
        stmt.bindLong(3, value.getStockId());
        stmt.bindLong(4, value.getQuantity());
        stmt.bindDouble(5, value.getUnitPrice());
        stmt.bindDouble(6, value.getTotalPrice());
      }
    };
    this.__deletionAdapterOfInvoiceItem = new EntityDeletionOrUpdateAdapter<InvoiceItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `invoice_items` WHERE `itemId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, InvoiceItem value) {
        stmt.bindLong(1, value.getItemId());
      }
    };
    this.__updateAdapterOfInvoiceItem = new EntityDeletionOrUpdateAdapter<InvoiceItem>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `invoice_items` SET `itemId` = ?,`invoiceId` = ?,`stockId` = ?,`quantity` = ?,`unitPrice` = ?,`totalPrice` = ? WHERE `itemId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, InvoiceItem value) {
        stmt.bindLong(1, value.getItemId());
        stmt.bindLong(2, value.getInvoiceId());
        stmt.bindLong(3, value.getStockId());
        stmt.bindLong(4, value.getQuantity());
        stmt.bindDouble(5, value.getUnitPrice());
        stmt.bindDouble(6, value.getTotalPrice());
        stmt.bindLong(7, value.getItemId());
      }
    };
    this.__preparedStmtOfDeleteItemsForInvoice = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM invoice_items WHERE invoiceId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final InvoiceItem invoiceItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfInvoiceItem.insert(invoiceItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final InvoiceItem invoiceItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfInvoiceItem.handle(invoiceItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final InvoiceItem invoiceItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfInvoiceItem.handle(invoiceItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteItemsForInvoice(final int invoiceId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteItemsForInvoice.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, invoiceId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteItemsForInvoice.release(_stmt);
    }
  }

  @Override
  public Flow<List<InvoiceItem>> getItemsForInvoice(final int invoiceId) {
    final String _sql = "SELECT * FROM invoice_items WHERE invoiceId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, invoiceId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoice_items"}, new Callable<List<InvoiceItem>>() {
      @Override
      public List<InvoiceItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "itemId");
          final int _cursorIndexOfInvoiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceId");
          final int _cursorIndexOfStockId = CursorUtil.getColumnIndexOrThrow(_cursor, "stockId");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "unitPrice");
          final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
          final List<InvoiceItem> _result = new ArrayList<InvoiceItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final InvoiceItem _item;
            final int _tmpItemId;
            _tmpItemId = _cursor.getInt(_cursorIndexOfItemId);
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final int _tmpStockId;
            _tmpStockId = _cursor.getInt(_cursorIndexOfStockId);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final double _tmpUnitPrice;
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice);
            final double _tmpTotalPrice;
            _tmpTotalPrice = _cursor.getDouble(_cursorIndexOfTotalPrice);
            _item = new InvoiceItem(_tmpItemId,_tmpInvoiceId,_tmpStockId,_tmpQuantity,_tmpUnitPrice,_tmpTotalPrice);
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
  public Flow<List<InvoiceItem>> getItemsByDateRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM invoice_items WHERE invoiceId IN (SELECT invoiceId FROM invoices WHERE date BETWEEN ? AND ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"invoice_items","invoices"}, new Callable<List<InvoiceItem>>() {
      @Override
      public List<InvoiceItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "itemId");
          final int _cursorIndexOfInvoiceId = CursorUtil.getColumnIndexOrThrow(_cursor, "invoiceId");
          final int _cursorIndexOfStockId = CursorUtil.getColumnIndexOrThrow(_cursor, "stockId");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "unitPrice");
          final int _cursorIndexOfTotalPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPrice");
          final List<InvoiceItem> _result = new ArrayList<InvoiceItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final InvoiceItem _item;
            final int _tmpItemId;
            _tmpItemId = _cursor.getInt(_cursorIndexOfItemId);
            final int _tmpInvoiceId;
            _tmpInvoiceId = _cursor.getInt(_cursorIndexOfInvoiceId);
            final int _tmpStockId;
            _tmpStockId = _cursor.getInt(_cursorIndexOfStockId);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final double _tmpUnitPrice;
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice);
            final double _tmpTotalPrice;
            _tmpTotalPrice = _cursor.getDouble(_cursorIndexOfTotalPrice);
            _item = new InvoiceItem(_tmpItemId,_tmpInvoiceId,_tmpStockId,_tmpQuantity,_tmpUnitPrice,_tmpTotalPrice);
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
