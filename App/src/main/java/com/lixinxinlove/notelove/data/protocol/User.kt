package com.lixinxinlove.notelove.data.protocol

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  用户实体类
 */

@Entity(tableName = "user")
data class User constructor(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0
) {
    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "phone")
    var phone: String? = null


    @ColumnInfo(name = "token")
    var token: String? = null

    // 必须有公共构造方法
    constructor() : this(0)

    override fun toString(): String {
        return "User(id=$id, name=$name, phone=$phone, token=$token)"
    }

}



