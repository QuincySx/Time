package com.smallraw.time

import android.app.Application
import com.smallraw.time.base.RudenessScreenHelper
import com.smallraw.time.db.AppDatabase


public class App : Application() {
    private lateinit var mApp: App;
    private lateinit var mAppExecutors: AppExecutors;

    override fun onCreate() {
        super.onCreate()
        mApp = this;
        mAppExecutors = AppExecutors()
        RudenessScreenHelper(this, 360F).activate()
    }

    fun getInstance(): App {
        return mApp;
    }

    fun getDatabase(): AppDatabase {
        return AppDatabase.getInstance(this, mAppExecutors)
    }

    fun getRepository(): DataRepository {
        return DataRepository.getInstance(getDatabase())
    }

    fun getAppExecutors(): AppExecutors {
        return mAppExecutors
    }
}