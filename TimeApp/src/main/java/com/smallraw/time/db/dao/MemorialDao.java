package com.smallraw.time.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.smallraw.time.db.entity.MemorialEntity;

import java.util.List;

@Dao
public interface MemorialDao {
    @Query("SELECT COUNT(*) FROM memorial")
    int count();

    @Insert
    long insert(MemorialEntity memorialEntity);

    @Insert
    List<Long> insertAll(MemorialEntity[] memorialEntity);

    @Query("SELECT * FROM memorial")
    List<MemorialEntity> selectAll();

    @Query("SELECT * FROM memorial WHERE strike = 0 AND archive = 0")
    List<MemorialEntity> selectActive();

    @Query("SELECT * FROM memorial WHERE id = :id")
    MemorialEntity selectById(long id);

    @Update
    int update(MemorialEntity memorialEntity);

    @Query("DELETE FROM memorial WHERE id = :id")
    int deleteById(long id);

    @Query("DELETE FROM memorial")
    int deleteAll();
}