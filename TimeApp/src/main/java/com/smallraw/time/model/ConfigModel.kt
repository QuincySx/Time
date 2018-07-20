package com.smallraw.time.model

import com.smallraw.time.App
import com.smallraw.time.db.entity.ConfigEntity
import java.util.*

public class ConfigModel {
    companion object {
        @JvmStatic
        public fun set(key: String, value: String) {
            set(key, value, 1000 * 60 * 60 * 2)
        }

        @JvmStatic
        public fun set(key: String, value: String, time: Long) {
            val configDao = App.getInstance().getDatabase().configDao()

            val findByKey = configDao.findByKey(key)
            if (findByKey.size > 0) {
                val get = findByKey.get(0)
                get.value = value
                get.createTime = Date()
                get.overTime = time
                configDao.update(get)
            } else {
                val configEntity = ConfigEntity()
                configEntity.name = key
                configEntity.value = value
                configEntity.createTime = Date()
                configEntity.overTime = time
                configDao.insert(configEntity)
            }
        }

        @JvmStatic
        public fun get(key: String): String {
            return get(key, true)
        }

        @JvmStatic
        public fun get(key: String, checkTimeout: Boolean): String {
            val configDao = App.getInstance().getDatabase().configDao()

            val findByKey = configDao.findByKey(key)
            if (findByKey.size > 0) {
                val get = findByKey.get(0)
                if (!checkTimeout) {
                    return get.value
                }
                val time = get.createTime.time + get.overTime
                val nowTime = Date().time
                if (nowTime < time) {
                    return get.value
                }
            }
            return ""
        }
    }
}

