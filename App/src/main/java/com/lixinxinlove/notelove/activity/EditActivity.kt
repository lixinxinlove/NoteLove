package com.lixinxinlove.notelove.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.R
import com.lixinxinlove.notelove.data.protocol.Note
import com.lixinxinlove.user.data.db.NoteDataBaseHelper
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.content_edit.*

/**
 * 编辑页面
 */
class EditActivity : BaseActivity(), View.OnClickListener {

    private val TAG = "EditActivity"


    private var note = Note(0)

    override fun layoutId(): Int {
        return R.layout.activity_edit
    }

    override fun listener() {

        tvSave.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvSave -> {
                Toast.makeText(mContext, "保存", Toast.LENGTH_SHORT).show()
                note.time = System.currentTimeMillis()
                note.title = mTitle.text.toString()
                note.info = mInfo.text.toString()
                note.editTime = System.currentTimeMillis()
                note.theme = 0

                onSave(note)
            }
        }
    }


    private fun onSave(note: Note) {

        NoteDataBaseHelper.getInstance(mContext).appDataBase.noteDao().insert(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Long> {
                override fun onSuccess(t: Long) {
                    Log.e(TAG, "onSuccess")
                    setResult(Activity.RESULT_OK)
                    finish()
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG, "onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError")
                }
            })
    }


}
