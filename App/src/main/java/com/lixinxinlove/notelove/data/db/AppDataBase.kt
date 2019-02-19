package com.lixinxinlove.user.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lixinxinlove.notelove.data.dao.NoteDao
import com.lixinxinlove.notelove.data.dao.UserDao
import com.lixinxinlove.notelove.data.protocol.Note
import com.lixinxinlove.notelove.data.protocol.User

@Database(entities = arrayOf(Note::class, User::class), version = 3)
abstract class AppDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun userDao(): UserDao
}