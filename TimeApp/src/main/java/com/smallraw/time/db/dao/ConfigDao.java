package com.smallraw.time.db.dao;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.smallraw.time.db.entity.ConfigEntity;
import com.smallraw.time.db.entity.MemorialEntity;

import java.util.List;

public interface ConfigDao {
    @Query("SELECT COUNT(*) FROM config")
    int count();

    @Insert
    long insert(ConfigEntity configEntity);

    @Query("SELECT * FROM config WHERE id = :id")
    ConfigEntity findById(long id);

    @Query("SELECT * FROM config WHERE name= :name")
    List<ConfigEntity> findByKey(String name);

    @Update
    int update(ConfigEntity configEntity);
}
