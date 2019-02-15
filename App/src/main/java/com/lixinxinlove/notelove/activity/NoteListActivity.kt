package com.lixinxinlove.notelove.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.R
import com.lixinxinlove.notelove.adapter.NoteListAdapter
import com.lixinxinlove.notelove.data.protocol.Note
import com.lixinxinlove.user.data.db.NoteDataBaseHelper
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * note 列表
 */
class NoteListActivity : BaseActivity() {

    private val TAG = "NoteListActivity"

    private lateinit var mAdapter: NoteListAdapter

    private var mData: MutableList<Note>? = null

    override fun layoutId(): Int {
        return R.layout.activity_note_list
    }

    override fun listener() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        mData = mutableListOf()
        mAdapter = NoteListAdapter(mData)
        mNoteRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mNoteRecyclerView.adapter = mAdapter
        getNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun getNotes() {
        NoteDataBaseHelper.getInstance(mContext).appDataBase.noteDao().getNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<MutableList<Note>> {
                override fun onSuccess(t: MutableList<Note>) {
                    Log.e(TAG, "onSuccess")
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
