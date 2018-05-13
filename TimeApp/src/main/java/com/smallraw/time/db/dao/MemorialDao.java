package com.smallraw.time.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.smallraw.time.db.entity.MemorialEntity;

@Dao
public interface MemorialDao {
    @Query("SELECT COUNT(*) FROM memorial")
    int count();

    @Insert
    long insert(MemorialEntity memorialEntity);

    @Insert
    long insertAll(MemorialEntity[] memorialEntity);

    @Query("SELECT * FROM memorial")
    Cursor selectAll();

    @Query("SELECT * FROM memorial WHERE id = :id")
    Cursor selectById(long id);

    @Update
    int update(MemorialEntity memorialEntity);

    @Query("DELETE FROM memorial WHERE id = :id")
    int deleteById(long id);

    @Query("DELETE FROM memorial")
    int deleteAll();
}