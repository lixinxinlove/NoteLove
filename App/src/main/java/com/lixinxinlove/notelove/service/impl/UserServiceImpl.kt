package com.lixinxinlove.notelove.service.impl

import com.lixinxinlove.notelove.data.protocol.User
import com.lixinxinlove.notelove.repository.UserRepository
import com.lixinxinlove.notelove.service.UserService
import io.reactivex.Observable

class UserServiceImpl : UserService {

    private var userRepository = UserRepository()

    override fun login(phone: String, password: String): Observable<User> {
        return userRepository.login(phone, password)
    }
}