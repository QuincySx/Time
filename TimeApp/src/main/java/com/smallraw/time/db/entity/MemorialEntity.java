package com.smallraw.time.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = MemorialEntity.TABLE_NAME)
public class MemorialEntity {
    public final static String TABLE_NAME = "memorial";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "time")
    private Date time;
    @ColumnInfo(name = "createTime")
    private Date createTime;
    @ColumnInfo(name = "strike")
    private boolean strike;

    public MemorialEntity() {
    }

    @Ignore
    public MemorialEntity(String name, String description, Date time, Date createTime, boolean
            strike) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.createTime = createTime;
        this.strike = strike;
    }

    @Ignore
    public MemorialEntity(Long id, String name, String description, Date time, Date createTime,
                          boolean strike) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
        this.createTime = createTime;
        this.strike = strike;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isStrike() {
        return strike;
    }

    public void setStrike(boolean strike) {
        this.strike = strike;
    }

    @Override
    public String toString() {
        return "MemorialEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", createTime=" + createTime +
                ", strike=" + strike +
                '}';
    }
}
