package com.smallraw.time.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity(tableName = MemorialEntity.TABLE_NAME)
public class MemorialEntity implements Parcelable {
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
    public MemorialEntity(String name, String description, int type, String color,
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.type);
        dest.writeString(this.color);
        dest.writeLong(this.beginTime != null ? this.beginTime.getTime() : -1);
        dest.writeLong(this.endTime != null ? this.endTime.getTime() : -1);
        dest.writeLong(this.createTime != null ? this.createTime.getTime() : -1);
        dest.writeByte(this.strike ? (byte) 1 : (byte) 0);
        dest.writeByte(this.archive ? (byte) 1 : (byte) 0);
    }

    protected MemorialEntity(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.type = in.readInt();
        this.color = in.readString();
        long tmpBeginTime = in.readLong();
        this.beginTime = tmpBeginTime == -1 ? null : new Date(tmpBeginTime);
        long tmpEndTime = in.readLong();
        this.endTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
        long tmpCreateTime = in.readLong();
        this.createTime = tmpCreateTime == -1 ? null : new Date(tmpCreateTime);
        this.strike = in.readByte() != 0;
        this.archive = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MemorialEntity> CREATOR = new Parcelable.Creator<MemorialEntity>() {
        @Override
        public MemorialEntity createFromParcel(Parcel source) {
            return new MemorialEntity(source);
        }

        @Override
        public MemorialEntity[] newArray(int size) {
            return new MemorialEntity[size];
        }
    };
}
