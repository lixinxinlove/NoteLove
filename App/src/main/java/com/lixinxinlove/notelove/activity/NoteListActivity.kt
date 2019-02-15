package com.lixinxinlove.notelove.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
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
        mNoteSwipeRefreshLayout.setColorSchemeResources(R.color.common_blue_light)
        mNoteSwipeRefreshLayout.setOnRefreshListener {
            getNotes()
        }

        fab.setOnClickListener { view ->
            startActivity(Intent(this, EditActivity::class.java))
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        mData = mutableListOf()
        mNoteRecyclerView.layoutManager = LinearLayoutManager(mContext)
        getNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun getNotes() {
        mNoteSwipeRefreshLayout.isRefreshing = true
        NoteDataBaseHelper.getInstance(mContext).appDataBase.noteDao().getNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<MutableList<Note>> {
                override fun onSuccess(t: MutableList<Note>) {
                    Log.e(TAG, "onSuccess")
                    mData = t
                    mAdapter=NoteListAdapter(mData)
                    mNoteRecyclerView.adapter=mAdapter
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG, "onSubscribe")
                    mNoteSwipeRefreshLayout.isRefreshing = false
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError")
                }
            })
    }
}
