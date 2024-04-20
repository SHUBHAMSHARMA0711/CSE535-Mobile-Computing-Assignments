package com.example.assignment3question1;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
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
public final class OrientationDAO_Impl implements OrientationDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<OrientationData> __insertionAdapterOfOrientationData;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllOrientationData;

  public OrientationDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrientationData = new EntityInsertionAdapter<OrientationData>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `orientation_data` (`time`,`pitch`,`roll`,`yaw`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final OrientationData entity) {
        if (entity.getTime() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getTime());
        }
        statement.bindDouble(2, entity.getPitch());
        statement.bindDouble(3, entity.getRoll());
        statement.bindDouble(4, entity.getYaw());
      }
    };
    this.__preparedStmtOfDeleteAllOrientationData = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM orientation_data";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrientationData(final OrientationData orientationData,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOrientationData.insert(orientationData);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllOrientationData(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllOrientationData.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllOrientationData.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getOrientationDataByTime(final String time,
      final Continuation<? super OrientationData> $completion) {
    final String _sql = "SELECT * FROM orientation_data WHERE time = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (time == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, time);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<OrientationData>() {
      @Override
      @Nullable
      public OrientationData call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfPitch = CursorUtil.getColumnIndexOrThrow(_cursor, "pitch");
          final int _cursorIndexOfRoll = CursorUtil.getColumnIndexOrThrow(_cursor, "roll");
          final int _cursorIndexOfYaw = CursorUtil.getColumnIndexOrThrow(_cursor, "yaw");
          final OrientationData _result;
          if (_cursor.moveToFirst()) {
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
            }
            final float _tmpPitch;
            _tmpPitch = _cursor.getFloat(_cursorIndexOfPitch);
            final float _tmpRoll;
            _tmpRoll = _cursor.getFloat(_cursorIndexOfRoll);
            final float _tmpYaw;
            _tmpYaw = _cursor.getFloat(_cursorIndexOfYaw);
            _result = new OrientationData(_tmpTime,_tmpPitch,_tmpRoll,_tmpYaw);
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
  public Object getAllOrientationData(
      final Continuation<? super List<OrientationData>> $completion) {
    final String _sql = "SELECT * FROM orientation_data";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<OrientationData>>() {
      @Override
      @NonNull
      public List<OrientationData> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
          final int _cursorIndexOfPitch = CursorUtil.getColumnIndexOrThrow(_cursor, "pitch");
          final int _cursorIndexOfRoll = CursorUtil.getColumnIndexOrThrow(_cursor, "roll");
          final int _cursorIndexOfYaw = CursorUtil.getColumnIndexOrThrow(_cursor, "yaw");
          final List<OrientationData> _result = new ArrayList<OrientationData>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrientationData _item;
            final String _tmpTime;
            if (_cursor.isNull(_cursorIndexOfTime)) {
              _tmpTime = null;
            } else {
              _tmpTime = _cursor.getString(_cursorIndexOfTime);
            }
            final float _tmpPitch;
            _tmpPitch = _cursor.getFloat(_cursorIndexOfPitch);
            final float _tmpRoll;
            _tmpRoll = _cursor.getFloat(_cursorIndexOfRoll);
            final float _tmpYaw;
            _tmpYaw = _cursor.getFloat(_cursorIndexOfYaw);
            _item = new OrientationData(_tmpTime,_tmpPitch,_tmpRoll,_tmpYaw);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
