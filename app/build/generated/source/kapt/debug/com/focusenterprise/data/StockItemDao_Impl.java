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
public final class StockItemDao_Impl implements StockItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StockItem> __insertionAdapterOfStockItem;

  private final EntityDeletionOrUpdateAdapter<StockItem> __deletionAdapterOfStockItem;

  private final EntityDeletionOrUpdateAdapter<StockItem> __updateAdapterOfStockItem;

  private final SharedSQLiteStatement __preparedStmtOfReduceStock;

  public StockItemDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStockItem = new EntityInsertionAdapter<StockItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `stock_items` (`stockId`,`name`,`purchasePrice`,`sellingPrice`,`quantity`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, StockItem value) {
        stmt.bindLong(1, value.getStockId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindDouble(3, value.getPurchasePrice());
        stmt.bindDouble(4, value.getSellingPrice());
        stmt.bindLong(5, value.getQuantity());
      }
    };
    this.__deletionAdapterOfStockItem = new EntityDeletionOrUpdateAdapter<StockItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `stock_items` WHERE `stockId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, StockItem value) {
        stmt.bindLong(1, value.getStockId());
      }
    };
    this.__updateAdapterOfStockItem = new EntityDeletionOrUpdateAdapter<StockItem>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `stock_items` SET `stockId` = ?,`name` = ?,`purchasePrice` = ?,`sellingPrice` = ?,`quantity` = ? WHERE `stockId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, StockItem value) {
        stmt.bindLong(1, value.getStockId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindDouble(3, value.getPurchasePrice());
        stmt.bindDouble(4, value.getSellingPrice());
        stmt.bindLong(5, value.getQuantity());
        stmt.bindLong(6, value.getStockId());
      }
    };
    this.__preparedStmtOfReduceStock = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE stock_items SET quantity = quantity - ? WHERE stockId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final StockItem stockItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfStockItem.insert(stockItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final StockItem stockItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfStockItem.handle(stockItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final StockItem stockItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfStockItem.handle(stockItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int reduceStock(final int stockId, final int soldQty) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfReduceStock.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, soldQty);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, stockId);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfReduceStock.release(_stmt);
    }
  }

  @Override
  public Flow<List<StockItem>> getAllStockItems() {
    final String _sql = "SELECT `stock_items`.`stockId` AS `stockId`, `stock_items`.`name` AS `name`, `stock_items`.`purchasePrice` AS `purchasePrice`, `stock_items`.`sellingPrice` AS `sellingPrice`, `stock_items`.`quantity` AS `quantity` FROM stock_items ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"stock_items"}, new Callable<List<StockItem>>() {
      @Override
      public List<StockItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStockId = 0;
          final int _cursorIndexOfName = 1;
          final int _cursorIndexOfPurchasePrice = 2;
          final int _cursorIndexOfSellingPrice = 3;
          final int _cursorIndexOfQuantity = 4;
          final List<StockItem> _result = new ArrayList<StockItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final StockItem _item;
            final int _tmpStockId;
            _tmpStockId = _cursor.getInt(_cursorIndexOfStockId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final double _tmpSellingPrice;
            _tmpSellingPrice = _cursor.getDouble(_cursorIndexOfSellingPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            _item = new StockItem(_tmpStockId,_tmpName,_tmpPurchasePrice,_tmpSellingPrice,_tmpQuantity);
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
  public Flow<StockItem> getStockItemById(final int stockId) {
    final String _sql = "SELECT * FROM stock_items WHERE stockId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, stockId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"stock_items"}, new Callable<StockItem>() {
      @Override
      public StockItem call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStockId = CursorUtil.getColumnIndexOrThrow(_cursor, "stockId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfSellingPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "sellingPrice");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final StockItem _result;
          if(_cursor.moveToFirst()) {
            final int _tmpStockId;
            _tmpStockId = _cursor.getInt(_cursorIndexOfStockId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final double _tmpSellingPrice;
            _tmpSellingPrice = _cursor.getDouble(_cursorIndexOfSellingPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            _result = new StockItem(_tmpStockId,_tmpName,_tmpPurchasePrice,_tmpSellingPrice,_tmpQuantity);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
