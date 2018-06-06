package com.smallraw.time.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = MemorialTopEntity.TABLE_NAME,
        foreignKeys = {@ForeignKey(entity = MemorialEntity.class,
                parentColumns = "id",
                childColumns = "memorial_id")})
public class MemorialTopEntity {
    public final static String TABLE_NAME = "memorial_top";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "memorial_id")
    private Long memorial_id;
    @ColumnInfo(name = "type")
    private Long type;//0 主页 1 归档页面
    @ColumnInfo(name = "createTime")
    private Date createTime;

    public MemorialTopEntity() {
    }

    @Ignore
    public MemorialTopEntity(Long memorial_id) {
        this.memorial_id = memorial_id;
        this.createTime = new Date();
    }

    @Ignore
    public MemorialTopEntity(Long memorial_id, Long type) {
        this.memorial_id = memorial_id;
        this.createTime = new Date();
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemorial_id() {
        return memorial_id;
    }

    public void setMemorial_id(Long memorial_id) {
        this.memorial_id = memorial_id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
}
