package com.lixinxinlove.notelove.data.protocol

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0                 //id
) {
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

    // 必须有公共构造方法
    constructor() : this(0)

    override fun toString(): String {
        return "Note(id=$id, title=$title, info=$info, time=$time, theme=$theme, editTime=$editTime)"
    }
}