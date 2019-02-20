package com.lixinxinlove.notelove.data.protocol

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0                 //id
) : Parcelable {
    @ColumnInfo(name = "title")
    var title: String? = null       //标题
    @ColumnInfo(name = "info")
    var info: String? = null       //文本内容
    @ColumnInfo(name = "time")
    var time: Long = 0             //创建时间
    @ColumnInfo(name = "theme")
    var theme: Int? = 0             //页面主题
    @ColumnInfo(name = "editTime")
    var editTime: Long = 0          //编辑时间
    @ColumnInfo(name = "status")
    var status: Int? = 0             //状态

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        title = parcel.readString()
        info = parcel.readString()
        time = parcel.readLong()
        theme = parcel.readValue(Int::class.java.classLoader) as? Int
        editTime = parcel.readLong()
        status = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    // 必须有公共构造方法
    constructor() : this(0)

    override fun toString(): String {
        return "Note(id=$id, title=$title, info=$info, time=$time, theme=$theme, editTime=$editTime)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(info)
        parcel.writeLong(time)
        parcel.writeValue(theme)
        parcel.writeLong(editTime)
        parcel.writeValue(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}