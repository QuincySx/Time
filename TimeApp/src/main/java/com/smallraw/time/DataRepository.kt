package com.smallraw.time

import com.smallraw.time.db.AppDatabase


class DataRepository {
    private val mDatabase: AppDatabase

    companion object {
        @JvmStatic
        private var sInstance: DataRepository? = null

        @JvmStatic
        fun getInstance(database: AppDatabase): DataRepository {
            if (sInstance == null) {
                synchronized(DataRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = DataRepository(database)
                    }
                }
            }
            return sInstance!!
        }
    }


    constructor(database: AppDatabase) {
        mDatabase = database
    }

}
