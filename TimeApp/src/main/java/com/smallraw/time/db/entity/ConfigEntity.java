package com.smallraw.time.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = ConfigEntity.TABLE_NAME)
public class ConfigEntity {
    public static final String TABLE_NAME = "config";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "overTime")
    private Long overTime;
    @ColumnInfo(name = "createTime")
    private Date createTime;

    public ConfigEntity() {
    }

    @Ignore
    public ConfigEntity(String name, String value, Long overTime) {
        this.name = name;
        this.value = value;
        this.overTime = overTime;
        this.createTime = new Date();
    }

    @Ignore
    public ConfigEntity(Long id, String name, String value, Long overTime, Date createTime) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.overTime = overTime;
        this.createTime = createTime;
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

    public void setName(String key) {
        this.name = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getOverTime() {
        return overTime;
    }

    public void setOverTime(Long overTime) {
        this.overTime = overTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ConfigEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", overTime=" + overTime +
                ", createTime=" + createTime +
                '}';
    }
}
