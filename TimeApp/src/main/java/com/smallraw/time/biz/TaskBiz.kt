package com.smallraw.time.biz

import android.app.Application
import com.smallraw.time.App
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.db.entity.MemorialTopEntity

fun deleteTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isStrike = true
    (app as App).getRepository().update(memorialEntity)
}

fun unDeleteTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isStrike = false
    (app as App).getRepository().update(memorialEntity)
}

fun archivingTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isArchive = true
    (app as App).getRepository().update(memorialEntity)
}

fun unArchivingTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isArchive = false
    (app as App).getRepository().update(memorialEntity)
}

fun isTopTask(app: Application, memorialEntity: MemorialEntity): Boolean {
    return (app as App).getRepository().isTopTask(memorialEntity.id)
}

fun topTask(app: Application, memorialEntity: MemorialEntity) {
    val memorialEntity = MemorialTopEntity(memorialEntity.id)
    (app as App).getRepository().insertTopTask(memorialEntity)
}

fun unTopTask(app: Application, memorialEntity: MemorialEntity) {
    (app as App).getRepository().deleteTopTask(memorialEntity.id)
}
