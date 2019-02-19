package com.lixinxinlove.notelove.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.ext.convert
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.R
import com.lixinxinlove.notelove.app.NoteApp
import com.lixinxinlove.notelove.data.api.NoteApi
import com.lixinxinlove.notelove.data.protocol.User
import com.lixinxinlove.notelove.service.UserService
import com.lixinxinlove.notelove.service.impl.UserServiceImpl
import com.lixinxinlove.user.data.db.NoteDataBaseHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录
 */
class LoginActivity : BaseActivity() {

    private lateinit var userService: UserService

    override fun layoutId(): Int {
        return R.layout.activity_login

    }

    override fun listener() {
        btnSingIn.setOnClickListener {
            //登录
            singIn(etPhone.text.toString().trim(), etPassword.text.toString().trim())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userService = UserServiceImpl()
    }


    @SuppressLint("CheckResult")
    fun singIn(phone: String, password: String) {
        mProgressLoading.showLoading()
        RetrofitFactory.instance.create(NoteApi::class.java).login(phone, password).convert()
            .map(object : Function<User, User> {
                override fun apply(t: User): User {
                    NoteDataBaseHelper.getInstance(applicationContext).appDataBase.userDao().insert(t)
                    Log.e("singIn", "保存数据")
                    return t
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    val userInfo = it
                    Log.e("登录成功", userInfo.toString())
                    Log.e("Single", "onSuccess")
                    NoteApp.isLogin = true
                    NoteApp.user = userInfo
                    finish()
                },
                onError = {
                    mProgressLoading.hideLoading()
                    Log.e("网络异常", "onError")
                },
                onComplete = {
                    mProgressLoading.hideLoading()
                })
    }

}
