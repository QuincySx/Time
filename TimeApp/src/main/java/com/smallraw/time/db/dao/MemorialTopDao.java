package com.smallraw.time.db.dao;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import com.smallraw.time.db.entity.MemorialTopEntity;

import java.util.List;

@Dao
public interface MemorialTopDao {
    @RawQuery
    List<MemorialTopEntity> select(SupportSQLiteQuery sql);

    @Query("SELECT COUNT(*) FROM memorial_top")
    int count();

    @Insert
    long insert(MemorialTopEntity memorialTopEntity);

    @Query("SELECT COUNT(*) FROM memorial_top WHERE memorial_id = :taskId AND type = :type")
    int isTopTaskByTaskId(Long taskId, Long type);

    @Insert
    List<Long> insertAll(MemorialTopEntity[] memorialTopEntities);

    @Query("SELECT * FROM memorial_top ORDER BY createTime DESC")
    List<MemorialTopEntity> selectAll();

    @Query("SELECT * FROM memorial_top WHERE type = :type ORDER BY createTime DESC")
    List<MemorialTopEntity> selectAllByType(int type);

    @Update
    int update(MemorialTopEntity memorialEntity);

    @Query("DELETE FROM memorial_top WHERE id = :id")
    int deleteById(long id);

    @Query("DELETE FROM memorial_top WHERE memorial_id = :id")
    int deleteByTaskId(long id);

    @Query("DELETE FROM memorial_top")
    int deleteAll();

    @Query("DELETE FROM memorial_top WHERE memorial_id = :taskId")
    int deleteTaskByTaskId(Long taskId);
}