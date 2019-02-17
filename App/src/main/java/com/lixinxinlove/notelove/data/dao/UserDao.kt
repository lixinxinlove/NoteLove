package com.lixinxinlove.notelove.data.dao

import androidx.room.*
import com.lixinxinlove.notelove.data.protocol.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUsers(): Single<MutableList<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Int): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Single<Long>

    @Delete
    fun deleteUser(user: User): Single<Int>

    @Delete
    fun deleteAllUser(notes: List<User>): Single<Int>


}