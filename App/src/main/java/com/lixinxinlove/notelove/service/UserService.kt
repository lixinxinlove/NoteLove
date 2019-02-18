package com.lixinxinlove.notelove.service

import com.lixinxinlove.notelove.data.protocol.User
import io.reactivex.Observable


/**
 * 用户
 */
interface  UserService{
    //用户登录
    fun login(phone: String, password: String): Observable<User>
}