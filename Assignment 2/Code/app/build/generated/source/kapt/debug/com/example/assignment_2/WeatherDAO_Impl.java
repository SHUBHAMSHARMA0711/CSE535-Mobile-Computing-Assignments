package com.example.assignment_2;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDAO_Impl implements WeatherDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WeatherData> __insertionAdapterOfWeatherData;

  public WeatherDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherData = new EntityInsertionAdapter<WeatherData>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `weather_data` (`date`,`curTemp`,`minTemp`,`maxTemp`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WeatherData entity) {
        if (entity.getDate() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getDate());
        }
        if (entity.getCurTemp() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getCurTemp());
        }
        if (entity.getMinTemp() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getMinTemp());
        }
        if (entity.getMaxTemp() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getMaxTemp());
        }
      }
    };
  }

  @Override
  public Object insertWeatherData(final WeatherData weatherData,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWeatherData.insert(weatherData);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getWeatherDataByDate(final String date,
      final Continuation<? super WeatherData> $completion) {
    final String _sql = "SELECT * FROM weather_data WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WeatherData>() {
      @Override
      @Nullable
      public WeatherData call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCurTemp = CursorUtil.getColumnIndexOrThrow(_cursor, "curTemp");
          final int _cursorIndexOfMinTemp = CursorUtil.getColumnIndexOrThrow(_cursor, "minTemp");
          final int _cursorIndexOfMaxTemp = CursorUtil.getColumnIndexOrThrow(_cursor, "maxTemp");
          final WeatherData _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpCurTemp;
            if (_cursor.isNull(_cursorIndexOfCurTemp)) {
              _tmpCurTemp = null;
            } else {
              _tmpCurTemp = _cursor.getString(_cursorIndexOfCurTemp);
            }
            final String _tmpMinTemp;
            if (_cursor.isNull(_cursorIndexOfMinTemp)) {
              _tmpMinTemp = null;
            } else {
              _tmpMinTemp = _cursor.getString(_cursorIndexOfMinTemp);
            }
            final String _tmpMaxTemp;
            if (_cursor.isNull(_cursorIndexOfMaxTemp)) {
              _tmpMaxTemp = null;
            } else {
              _tmpMaxTemp = _cursor.getString(_cursorIndexOfMaxTemp);
            }
            _result = new WeatherData(_tmpDate,_tmpCurTemp,_tmpMinTemp,_tmpMaxTemp);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
