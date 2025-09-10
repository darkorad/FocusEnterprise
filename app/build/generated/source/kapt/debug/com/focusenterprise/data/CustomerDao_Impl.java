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
public final class CustomerDao_Impl implements CustomerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Customer> __insertionAdapterOfCustomer;

  private final EntityDeletionOrUpdateAdapter<Customer> __deletionAdapterOfCustomer;

  private final EntityDeletionOrUpdateAdapter<Customer> __updateAdapterOfCustomer;

  public CustomerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCustomer = new EntityInsertionAdapter<Customer>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `customers` (`customerId`,`name`,`pib`,`phone`,`email`,`address`,`totalDebt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Customer value) {
        stmt.bindLong(1, value.getCustomerId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getPib() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPib());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPhone());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmail());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAddress());
        }
        stmt.bindDouble(7, value.getTotalDebt());
      }
    };
    this.__deletionAdapterOfCustomer = new EntityDeletionOrUpdateAdapter<Customer>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `customers` WHERE `customerId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Customer value) {
        stmt.bindLong(1, value.getCustomerId());
      }
    };
    this.__updateAdapterOfCustomer = new EntityDeletionOrUpdateAdapter<Customer>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `customers` SET `customerId` = ?,`name` = ?,`pib` = ?,`phone` = ?,`email` = ?,`address` = ?,`totalDebt` = ? WHERE `customerId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Customer value) {
        stmt.bindLong(1, value.getCustomerId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getPib() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPib());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPhone());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getEmail());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAddress());
        }
        stmt.bindDouble(7, value.getTotalDebt());
        stmt.bindLong(8, value.getCustomerId());
      }
    };
  }

  @Override
  public void insert(final Customer customer) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCustomer.insert(customer);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Customer customer) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCustomer.handle(customer);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Customer customer) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCustomer.handle(customer);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Flow<List<Customer>> getAllCustomers() {
    final String _sql = "SELECT `customers`.`customerId` AS `customerId`, `customers`.`name` AS `name`, `customers`.`pib` AS `pib`, `customers`.`phone` AS `phone`, `customers`.`email` AS `email`, `customers`.`address` AS `address`, `customers`.`totalDebt` AS `totalDebt` FROM customers ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"customers"}, new Callable<List<Customer>>() {
      @Override
      public List<Customer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCustomerId = 0;
          final int _cursorIndexOfName = 1;
          final int _cursorIndexOfPib = 2;
          final int _cursorIndexOfPhone = 3;
          final int _cursorIndexOfEmail = 4;
          final int _cursorIndexOfAddress = 5;
          final int _cursorIndexOfTotalDebt = 6;
          final List<Customer> _result = new ArrayList<Customer>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Customer _item;
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPib;
            if (_cursor.isNull(_cursorIndexOfPib)) {
              _tmpPib = null;
            } else {
              _tmpPib = _cursor.getString(_cursorIndexOfPib);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final double _tmpTotalDebt;
            _tmpTotalDebt = _cursor.getDouble(_cursorIndexOfTotalDebt);
            _item = new Customer(_tmpCustomerId,_tmpName,_tmpPib,_tmpPhone,_tmpEmail,_tmpAddress,_tmpTotalDebt);
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
  public Flow<Customer> getCustomerById(final int customerId) {
    final String _sql = "SELECT * FROM customers WHERE customerId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, customerId);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"customers"}, new Callable<Customer>() {
      @Override
      public Customer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCustomerId = CursorUtil.getColumnIndexOrThrow(_cursor, "customerId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPib = CursorUtil.getColumnIndexOrThrow(_cursor, "pib");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfTotalDebt = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDebt");
          final Customer _result;
          if(_cursor.moveToFirst()) {
            final int _tmpCustomerId;
            _tmpCustomerId = _cursor.getInt(_cursorIndexOfCustomerId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpPib;
            if (_cursor.isNull(_cursorIndexOfPib)) {
              _tmpPib = null;
            } else {
              _tmpPib = _cursor.getString(_cursorIndexOfPib);
            }
            final String _tmpPhone;
            if (_cursor.isNull(_cursorIndexOfPhone)) {
              _tmpPhone = null;
            } else {
              _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final double _tmpTotalDebt;
            _tmpTotalDebt = _cursor.getDouble(_cursorIndexOfTotalDebt);
            _result = new Customer(_tmpCustomerId,_tmpName,_tmpPib,_tmpPhone,_tmpEmail,_tmpAddress,_tmpTotalDebt);
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
