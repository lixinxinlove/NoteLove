package com.lixinxinlove.notelove.server

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.gson.Gson
import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.ext.convert
import com.lixinxinlove.notelove.app.NoteApp
import com.lixinxinlove.notelove.data.api.NoteApi
import com.lixinxinlove.notelove.data.protocol.Note
import com.lixinxinlove.user.data.db.NoteDataBaseHelper
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * 这个用来同步数据
 */
class NoteSyncService : Service() {


    val TAG = "NoteSyncService"

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

        if (!NoteApp.isLogin) {
            return
        }

        NoteDataBaseHelper.getInstance(NoteApp.mContext).appDataBase.noteDao()
            .getNotesByStatus(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Note>> {
                override fun onSuccess(t: List<Note>) {
                    if (t.isNotEmpty()) {
                        Log.e("NoteSyncService", t.size.toString())
                        saveAll(t)
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

    @SuppressLint("CheckResult")
    fun saveAll(notes: List<Note>) {
        Log.e(TAG, "开始同步。。。。。")
        val gson = Gson()
        val data = gson.toJson(notes)
        RetrofitFactory.instance.create(NoteApi::class.java).save(data, NoteApp.user!!.id).convert()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Log.e(TAG, "同步成功。。。。。")
                    updateAll(notes)

                },
                onError = {
                    Log.e("网络异常", "onError")
                },
                onComplete = {
                    Log.e(TAG, "onComplete。。。。。")
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun updateAll(notes: List<Note>) {
        Log.e(TAG, "修改本地状态。。。。。")
        notes.stream().forEach { it.status = 1 }

        NoteDataBaseHelper.getInstance(NoteApp.mContext).appDataBase.noteDao().updateNoteList(notes)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                if (it > 0) {
                    Log.e(TAG, "修改本地成功")
                } else {
                    Log.e(TAG, "修改本地失败")
                }
            }

    }


}
