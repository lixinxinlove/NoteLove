package com.lixinxinlove.notelove.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.ext.convert
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.app.NoteApp
import com.lixinxinlove.notelove.config.NoteConfig
import com.lixinxinlove.notelove.data.api.NoteApi
import com.lixinxinlove.user.data.db.NoteDataBaseHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*


/**
 * 登录
 */
class LoginActivity : BaseActivity() {

    /**
     * 系统提供的振动服务
     */
    private var vibrator: Vibrator? = null
    private var shake: Animation? = null
    override fun layoutId(): Int {
        return com.lixinxinlove.notelove.R.layout.activity_login

    }

    override fun listener() {
        toolbar.setNavigationOnClickListener { finish() }

        btnSingIn.setOnClickListener {
            //登录

            if (etPhone.text.toString().isEmpty()) {
                etPhone.startAnimation(shake)
            }

            if (etPassword.text.toString().isEmpty()) {
                etPassword.startAnimation(shake)
            }
            //当电话号码为空的时候，就去振动手机提醒用户
            //  vibrator.vibrate(2000)
            // val pattern = longArrayOf(200, 200, 300, 300, 1000, 2000)
            //-1不重复 0循环振动 1；
            //vibrator.vibrate(pattern, -1)

            singIn(etPhone.text.toString().trim(), etPassword.text.toString().trim())
        }

        btnRegister.setOnClickListener {
            if (etPhone.text.toString().isEmpty()) {
                etPhone.startAnimation(shake)
                return@setOnClickListener
            }

            if (etPassword.text.toString().isEmpty()) {
                etPassword.startAnimation(shake)
                return@setOnClickListener
            }

            register(etPhone.text.toString().trim(), etPassword.text.toString().trim())
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shake = AnimationUtils.loadAnimation(this, com.lixinxinlove.notelove.R.anim.shake)
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator?

    }


    @SuppressLint("CheckResult")
    fun singIn(phone: String, password: String) {
        mProgressLoading.showLoading()
        RetrofitFactory.instance.create(NoteApi::class.java).login(phone, password).convert()
            .map { t ->
                NoteDataBaseHelper.getInstance(applicationContext).appDataBase.userDao().insert(t)
                Log.e("singIn", "保存数据")
                t
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    val userInfo = it
                    Log.e("登录成功", userInfo.toString())
                    Log.e("subscribeBy", "onSuccess")
                    NoteApp.isLogin = true
                    NoteApp.user = userInfo
                    var intent = Intent()
                    intent.action = NoteConfig.LOGIN_ACTION
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent)
                    finish()
                },
                onError = {
                    mProgressLoading.hideLoading()
                    Log.e("subscribeBy", "onError")
                    it.printStackTrace()
                },
                onComplete = {
                    mProgressLoading.hideLoading()
                    Log.e("subscribeBy", "onComplete")
                })
    }


    //注册
    @SuppressLint("CheckResult")
    private fun register(phone: String, password: String) {

        mProgressLoading.showLoading()
        RetrofitFactory.instance.create(NoteApi::class.java).register(phone, password).convert()
            .map { t ->
                NoteDataBaseHelper.getInstance(applicationContext).appDataBase.userDao().insert(t)
                Log.e("singIn", "保存数据")
                t
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    val userInfo = it
                    Log.e("注册成功", userInfo.toString())
                    Log.e("subscribeBy", "onSuccess")
                    NoteApp.isLogin = true
                    NoteApp.user = userInfo
                    var intent = Intent()
                    intent.action = NoteConfig.LOGIN_ACTION
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent)
                    finish()
                },
                onError = {
                    mProgressLoading.hideLoading()
                    Log.e("subscribeBy", "onError")
                    Toast.makeText(mContext,"注册失败",Toast.LENGTH_LONG).show()
                    it.printStackTrace()
                },
                onComplete = {
                    mProgressLoading.hideLoading()
                    Log.e("subscribeBy", "onComplete")
                })
    }

}

