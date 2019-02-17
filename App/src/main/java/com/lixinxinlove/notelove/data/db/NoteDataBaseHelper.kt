package com.lixinxinlove.user.data.db

import android.content.Context
import androidx.room.Room

/**
 * room rom
 *
 */
class NoteDataBaseHelper constructor(context: Context) {

    val appDataBase = Room.databaseBuilder(context, AppDataBase::class.java, "db_note").fallbackToDestructiveMigration().build()!!

    companion object {
        @Volatile
        var INSTANCE: NoteDataBaseHelper? = null

        fun getInstance(context: Context): NoteDataBaseHelper {
            if (INSTANCE == null) {
                synchronized(NoteDataBaseHelper::class) {
                    if (INSTANCE == null) {
                        INSTANCE = NoteDataBaseHelper(context.applicationContext)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}