package com.smallraw.time.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.smallraw.time.AppExecutors;
import com.smallraw.time.db.converter.DateConverter;
import com.smallraw.time.db.dao.MemorialDao;
import com.smallraw.time.db.entity.MemorialEntity;

@Database(entities = {MemorialEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "timeDb";
    private static AppDatabase sInstance;

    public abstract MemorialDao memorialDao();

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

                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(MemorialEntity.TABLE_NAME).exists()) {

        }
    }

}
