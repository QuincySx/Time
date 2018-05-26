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
    @ColumnInfo(name = "type")//0 累计日  1 倒数日
    private int type;
    @ColumnInfo(name = "color")
    private String color;
    @ColumnInfo(name = "beginTime")
    private Date beginTime;
    @ColumnInfo(name = "endTime")
    private Date endTime;
    @ColumnInfo(name = "createTime")
    private Date createTime;
    @ColumnInfo(name = "strike")
    private boolean strike;
    @ColumnInfo(name = "archive")
    private boolean archive;

    public MemorialEntity() {
    }

    @Ignore
    public MemorialEntity(String name, String description,int type, String color,
                          Date beginTime, Date endTime, Date createTime) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.color = color;
        this.beginTime = beginTime;
        this.endTime = endTime;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public String toString() {
        return "MemorialEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", color='" + color + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", strike=" + strike +
                ", archive=" + archive +
                '}';
    }
}
