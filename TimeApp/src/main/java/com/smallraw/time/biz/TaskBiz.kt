package com.smallraw.time.biz

import android.app.Application
import com.smallraw.time.App
import com.smallraw.time.db.entity.MemorialEntity

fun deleteTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isStrike = true
    memorialEntity.isArchive = false
    (app as App).getRepository().update(memorialEntity)
}

fun unDeleteTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isStrike = false
    memorialEntity.isArchive = false
    (app as App).getRepository().update(memorialEntity)
}

fun archivingTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isStrike = false
    memorialEntity.isArchive = true
    (app as App).getRepository().update(memorialEntity)
}

fun unArchivingTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isStrike = false
    memorialEntity.isArchive = false
    (app as App).getRepository().update(memorialEntity)
}

fun topTask(app: Application, memorialEntity: MemorialEntity) {
}

fun unTopTask(app: Application, memorialEntity: MemorialEntity) {
}
