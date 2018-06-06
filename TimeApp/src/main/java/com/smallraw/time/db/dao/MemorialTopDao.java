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

    @Insert
    List<Long> insertAll(MemorialTopEntity[] memorialTopEntities);

    @Query("SELECT * FROM memorial")
    List<MemorialTopEntity> selectAll();

    @Update
    int update(MemorialTopEntity memorialEntity);

    @Query("DELETE FROM memorial_top WHERE id = :id")
    int deleteById(long id);

    @Query("DELETE FROM memorial_top")
    int deleteAll();
}