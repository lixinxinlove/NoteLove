package com.lixinxinlove.notelove.data.dao

import androidx.room.*
import com.lixinxinlove.notelove.data.protocol.Note
import io.reactivex.Single

/**
 * NoteDao
 */

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getUsers(): Single<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getUser(id: Int): Single<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userInfoList: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userInfo: Note)

    @Delete
    fun deleteUser(userInfo: Note): Single<Int>

    @Delete
    fun deleteAllUser(users: List<Note>): Single<Int>

    @Transaction
    fun insertNetJobs(list: List<Note>) {
        //Timber.d("--- insert page start")
        for (j in list) {
            insert(j)
        }
        // Timber.d("--- insert page end")
    }
}