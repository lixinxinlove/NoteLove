package com.lixinxinlove.notelove.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.ext.convert
import com.lixinxinlove.notelove.data.api.NoteApi
import com.lixinxinlove.notelove.data.protocol.User
import io.reactivex.Observable

class UserRepository {

    /**
     *用户登录
     */
    fun login(phone: String, password: String): Observable<User> {
        return RetrofitFactory.instance.create(NoteApi::class.java).login(phone, password).convert()
    }

}