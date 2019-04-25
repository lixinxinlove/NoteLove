package com.lixinxinlove.notelove.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.config.NoteConfig

abstract class BaseNoteActivity : BaseActivity() {

    private lateinit var loginBroadcastReceiver: LoginBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBroadcastReceiver = LoginBroadcastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(NoteConfig.LOGIN_ACTION)
        LocalBroadcastManager.getInstance(mContext).registerReceiver(loginBroadcastReceiver, intentFilter)
    }


    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(loginBroadcastReceiver)
    }

    inner class LoginBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action == NoteConfig.LOGIN_ACTION) {
                loginAction()
            }
        }
    }

    abstract fun loginAction()


}