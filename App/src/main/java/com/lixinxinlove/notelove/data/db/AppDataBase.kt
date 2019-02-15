package com.lixinxinlove.user.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lixinxinlove.notelove.data.dao.NoteDao
import com.lixinxinlove.notelove.data.protocol.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}