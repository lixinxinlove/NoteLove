package com.lixinxinlove.notelove.data.protocol

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0

) {
    @ColumnInfo(name = "name")
    var name: String? = null
    @ColumnInfo(name = "gender")
    var gender: Byte? = 0
    @ColumnInfo(name = "age")
    var age: Int? = 0
    @ColumnInfo(name = "telphone")
    var telphone: String? = null

    // 必须有公共构造方法
    constructor() : this(0)

    override fun toString(): String {
        return "UserInfo(id=$id, name='$name', gender=$gender, age=$age, telphone='$telphone')"
    }

}