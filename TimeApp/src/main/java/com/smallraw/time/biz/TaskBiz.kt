package com.smallraw.time.biz

import android.app.Application
import com.smallraw.time.App
import com.smallraw.time.db.entity.MemorialEntity
import com.smallraw.time.db.entity.MemorialTopEntity

fun thoroughDeleteTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isStrike = true
    (app as App).getRepository().delete(memorialEntity.id)
}

fun thoroughDeleteTaskAll(app: Application, memorialEntitys: List<MemorialEntity>) {
    for (item in memorialEntitys) {
        (app as App).getRepository().deleteTopTaskAll(item.id)
    }
    (app as App).getRepository().deleteTask(memorialEntitys)
}

fun deleteTask(app: Application, memorialEntity: MemorialEntity) {
    memorialEntity.isStrike = true
    (app as App).getRepository().update(memorialEntity)
}

fun deleteTaskAll(app: Application, memorialEntitys: List<MemorialEntity>) {
    for (item in memorialEntitys) {
        item.isStrike = true
    }
    (app as App).getRepository().update(memorialEntitys)
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

fun isTopTask(app: Application, memorialEntity: MemorialEntity, type: Long): Boolean {
    return (app as App).getRepository().isTopTask(memorialEntity.id, type)
}

fun topTask(app: Application, memorialEntity: MemorialEntity, type: Long) {
    val memorialTopEntity = MemorialTopEntity(memorialEntity.id, type)
//    (app as App).getRepository().deleteTopTask(memorialEntity.id)
    (app as App).getRepository().insertTopTask(memorialTopEntity)
}

fun unTopTask(app: Application, memorialEntity: MemorialEntity, type: Long) {
    (app as App).getRepository().deleteTopTask(memorialEntity.id, type)
}
