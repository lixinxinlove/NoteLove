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
    fun getNotes(): Single<MutableList<Note>>

    @Query("SELECT * FROM note where status=:status")
    fun getNotesByStatus(status: Int): Single<MutableList<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNote(id: Int): Single<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(noteList: List<Note>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Single<Long>

    @Delete
    fun deleteNote(note: Note): Single<Int>

    @Delete
    fun deleteAllNote(notes: List<Note>): Single<Int>

    @Transaction
    fun insertNetJobs(list: List<Note>) {
        //Timber.d("--- insert page start")
        for (j in list) {
            insert(j)
        }
        // Timber.d("--- insert page end")
    }
}