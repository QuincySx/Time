package com.smallraw.time

import android.arch.persistence.db.SimpleSQLiteQuery
import android.util.Log
import com.smallraw.time.db.AppDatabase
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.db.entity.MemorialTopEntity


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

    /**
     * @param display 需要显示的内容 0::显示全部 1::显示倒数日 2::显示累计日
     * @param order 按条件排序 0::默认排序 1::日期排序 2::颜色排序
     * @return 返回按顺序查找的任务列表
     */
    fun getActiveTask(display: Int, order: Int): List<MemorialEntity> {
        var displayOption: Int? = null
        when (display) {
            0 -> {
                displayOption = null
            }
            1 -> {
                displayOption = 1
            }
            2 -> {
                displayOption = 0
            }
            else -> {
                displayOption = null
            }
        }
        var orderBy = "id"
        when (order) {
            0 -> {
                orderBy = "id"
            }
            1 -> {
                orderBy = "beginTime"
            }
            2 -> {
                orderBy = "color"
            }
            else -> {
                orderBy = "id"
            }
        }
        val query: SimpleSQLiteQuery
        if (displayOption == null) {
            query = SimpleSQLiteQuery("SELECT * FROM memorial WHERE strike = 0 AND archive = 0 ORDER BY " + orderBy + " DESC")
        } else {
            query = SimpleSQLiteQuery("SELECT * FROM memorial WHERE strike = 0 AND archive = 0 AND type = ? ORDER BY " + orderBy + " DESC",
                    arrayOf<Any>(displayOption))
        }
        Log.e("==sql==", query.sql + "   " + query.argCount)
        return mDatabase.memorialDao().select(query)
    }

    fun getTask(strike: Boolean = false, archive: Boolean = false): List<MemorialEntity> {
        val query = SimpleSQLiteQuery("SELECT * FROM memorial WHERE strike = ? AND archive = ? ORDER BY createTime DESC", arrayOf<Any>(strike, archive))
        return mDatabase.memorialDao().select(query)
    }

    fun getTaskStrike(strike: Boolean = false): List<MemorialEntity> {
        val query = SimpleSQLiteQuery("SELECT * FROM memorial WHERE strike = ? ORDER BY createTime DESC", arrayOf<Any>(strike))
        return mDatabase.memorialDao().select(query)
    }

    fun getTask(id: Long): MemorialEntity {
        return mDatabase.memorialDao().selectById(id)
    }

    fun insertTask(memorialEntity: MemorialEntity): Long {
        return mDatabase.memorialDao().insert(memorialEntity)
    }

    fun update(memorialEntity: MemorialEntity) {
        val deleteById = mDatabase.memorialDao().update(memorialEntity)
    }

    fun delete(id: Long) {
        mDatabase.memorialTopDao().deleteByTaskId(id)
        mDatabase.memorialDao().deleteById(id)
    }

    fun getTaskTopList(type: Int): MutableList<MemorialTopEntity> {
        return mDatabase.memorialTopDao().selectAllByType(type)
    }

    fun isTopTask(id: Long, type: Long): Boolean {
        val int = mDatabase.memorialTopDao().isTopTaskByTaskId(id, type)
        if (int > 0) {
            return true
        } else {
            return false
        }
    }

    fun insertTopTask(memorialEntity: MemorialTopEntity): Long {
        return mDatabase.memorialTopDao().insert(memorialEntity)
    }

    fun deleteTopTask(id: Long): Int {
        return mDatabase.memorialTopDao().deleteTaskByTaskId(id)
    }
}
