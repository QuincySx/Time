package com.smallraw.time.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.smallraw.time.AppExecutors;
import com.smallraw.time.db.converter.DateConverter;
import com.smallraw.time.db.dao.ConfigDao;
import com.smallraw.time.db.dao.MemorialDao;
import com.smallraw.time.db.dao.MemorialTopDao;
import com.smallraw.time.db.entity.ConfigEntity;
import com.smallraw.time.db.entity.MemorialEntity;
import com.smallraw.time.db.entity.MemorialTopEntity;

import java.util.Date;

@Database(entities = {
        ConfigEntity.class,
        MemorialEntity.class,
        MemorialTopEntity.class
}, version = 4, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "timeDb";
    private static AppDatabase sInstance;

    public abstract MemorialDao memorialDao();

    public abstract MemorialTopDao memorialTopDao();

    public abstract ConfigDao configDao();

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
                                    long insert = db.insert(MemorialEntity.TABLE_NAME,
                                            SQLiteDatabase.CONFLICT_REPLACE, contentValues);

                                    ContentValues contentTopValues = new ContentValues();
                                    contentTopValues.put("memorial_id", insert);
                                    contentTopValues.put("type", 0);
                                    contentTopValues.put("createTime", new Date().getTime());
                                    db.insert(MemorialTopEntity.TABLE_NAME,
                                            SQLiteDatabase.CONFLICT_REPLACE, contentTopValues);
                                    db.setTransactionSuccessful();
                                } finally {
                                    db.endTransaction();
                                }
                                database.setDatabaseCreated();
                            }
                        });
                    }
                })
                .addMigrations(
                        Migration1_2,
                        Migration2_3,
                        Migration3_4
                ).build();
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

    private static final Migration Migration1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String sql = "CREATE TABLE " + MemorialTopEntity.TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "memorial_id INTEGER," +
                    "createTime INTEGER," +
                    "FOREIGN KEY(memorial_id) REFERENCES " + MemorialEntity.TABLE_NAME + "(id)" +
                    ")";
            database.execSQL(sql);
        }
    };

    private static final Migration Migration2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String sql = "ALTER TABLE " + MemorialTopEntity.TABLE_NAME + " ADD COLUMN type INTEGER";
            database.execSQL(sql);
        }
    };

    private static final Migration Migration3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String sql = "CREATE TABLE " + ConfigEntity.TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "value TEXT," +
                    "overTime INTEGER," +
                    "createTime INTEGER" +
                    ")";
            database.execSQL(sql);
        }
    };
}
