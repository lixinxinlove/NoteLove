package com.lixinxinlove.notelove.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lixinxinlove.base.activity.BaseActivity
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
class NoteListActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.OnItemChildClickListener {


    private val TAG = "NoteListActivity"
    private val REQUEST_EDIT_CODE = 100

    private var mAdapter: NoteListAdapter? = null

    private var mData: MutableList<Note>? = null

    override fun layoutId(): Int {
        return com.lixinxinlove.notelove.R.layout.activity_note_list
    }

    override fun listener() {
        mNoteSwipeRefreshLayout.setColorSchemeResources(com.lixinxinlove.notelove.R.color.common_blue_light)
        mNoteSwipeRefreshLayout.setOnRefreshListener(this)

        fab.setOnClickListener { view ->
            startActivityForResult(Intent(this, EditActivity::class.java), REQUEST_EDIT_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        mData = mutableListOf()
        mAdapter = NoteListAdapter(mData)
        mAdapter!!.setOnItemChildClickListener(this)
        mNoteRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mNoteRecyclerView.adapter = mAdapter
        getNotes()
    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when (view!!.id) {
            com.lixinxinlove.notelove.R.id.tvDelete -> {
                onDelete(mAdapter!!.getItem(position), position)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_EDIT_CODE) {
                onRefresh()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.lixinxinlove.notelove.R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.lixinxinlove.notelove.R.id.action_settings -> {
                return true
            }

            com.lixinxinlove.notelove.R.id.action_sign_in -> {    //登录
                startActivity(Intent(mContext,LoginActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    @SuppressLint("RestrictedApi")
    override fun onPrepareOptionsPanel(view: View?, menu: Menu): Boolean {
        if (menu != null) {
            if (menu.javaClass == MenuBuilder::class.java) {
                try {
                    val m = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
                    m.isAccessible = true
                    m.invoke(menu, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        return super.onPrepareOptionsPanel(view, menu)
    }

   // onPreparePanel





    override fun onRefresh() {
        getNotes()
    }

    fun getNotes() {
        mNoteSwipeRefreshLayout.isRefreshing = true
        NoteDataBaseHelper.getInstance(mContext).appDataBase.noteDao().getNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<MutableList<Note>> {
                override fun onSuccess(t: MutableList<Note>) {
                    Log.e(TAG, "onSuccess")
                    //mAdapter!!.data.addAll(t)
                    mAdapter!!.setNewData(t)
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


    private fun onDelete(note: Note?, pos: Int) {
        if (note != null) {
            NoteDataBaseHelper.getInstance(mContext).appDataBase.noteDao().deleteNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Int> {
                    override fun onSuccess(t: Int) {
                        Log.e(TAG, "onSuccess")
                        mAdapter!!.data.removeAt(pos)
                        mAdapter!!.notifyItemRemoved(pos)
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


}
