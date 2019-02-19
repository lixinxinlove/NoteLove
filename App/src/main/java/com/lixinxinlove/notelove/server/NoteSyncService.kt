package com.lixinxinlove.notelove.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.lixinxinlove.notelove.app.NoteApp
import com.lixinxinlove.notelove.data.protocol.Note
import com.lixinxinlove.user.data.db.NoteDataBaseHelper
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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
        getNotes()
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        super.onDestroy()
    }


    //获取没有上传的数据
    fun getNotes() {
        NoteDataBaseHelper.getInstance(NoteApp.mContext).appDataBase.noteDao()
            .getNotesByStatus(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Note>> {
                override fun onSuccess(t: List<Note>) {
                    if (t.isNotEmpty()) {
                        Log.e("NoteSyncService", t.size.toString())
                    } else {
                        Log.e("NoteSyncService", "没有数据")
                    }
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e("NoteSyncService", "onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e("NoteSyncService", "onError")
                }
            })

    }


}
