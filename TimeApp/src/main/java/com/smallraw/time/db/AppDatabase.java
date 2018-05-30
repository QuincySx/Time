package com.smallraw.time.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.smallraw.time.AppExecutors;
import com.smallraw.time.db.converter.DateConverter;
import com.smallraw.time.db.dao.MemorialDao;
import com.smallraw.time.db.entity.MemorialEntity;

import java.util.Date;

@Database(entities = {MemorialEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "timeDb";
    private static AppDatabase sInstance;

    public abstract MemorialDao memorialDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), appExecutors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext, AppExecutors appExecutors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        appExecutors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("==","sdfsdfsdfsdfdsf");
                                AppDatabase database = AppDatabase.getInstance(appContext,
                                        appExecutors);
                                db.beginTransaction();
                                try {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("name", "孵化日");
                                    contentValues.put("description", "第一次遇到[记得]开心吗？");
                                    contentValues.put("type", 0);
                                    contentValues.put("color", "#139EED");
                                    contentValues.put("beginTime", new Date().getTime());
                                    contentValues.put("createTime", new Date().getTime());
                                    contentValues.put("strike", false);
                                    contentValues.put("archive", false);
                                    db.insert(MemorialEntity.TABLE_NAME, SQLiteDatabase
                                            .CONFLICT_REPLACE, contentValues);
                                    db.setTransactionSuccessful();
                                } finally {
                                    db.endTransaction();
                                }
                                database.setDatabaseCreated();
                            }
                        });
                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(MemorialEntity.TABLE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
