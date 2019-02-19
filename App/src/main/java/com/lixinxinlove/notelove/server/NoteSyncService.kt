package com.lixinxinlove.notelove.server

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 这个用来同步数据
 */
class NoteSyncService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}
