package com.lixinxinlove.notelove.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.lixinxinlove.notelove.data.protocol.User
import com.lixinxinlove.user.data.db.NoteDataBaseHelper
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * app 入口
 */
class NoteApp : Application() {

    companion object {
        var isLogin = false
        var user: User? = null
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        getUser()
    }

    private fun getUser() {
        NoteDataBaseHelper.getInstance(applicationContext).appDataBase.userDao().getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<User>> {
                override fun onSuccess(t: List<User>) {
                    if (t.isNotEmpty()) {
                        user = t[0]
                        isLogin = true
                        Log.e("App", user.toString())
                    } else {
                        isLogin = false
                        Log.e("App", "isNotEmpty")
                    }
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e("App", "onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e("App", "onError")
                    isLogin = false
                }
            })
    }


}