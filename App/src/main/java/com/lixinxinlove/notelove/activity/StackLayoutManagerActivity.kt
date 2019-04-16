package com.lixinxinlove.notelove.activity

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrEventHeader
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.R
import com.lixinxinlove.notelove.adapter.ImageListAdapter
import com.lixinxinlove.notelove.layoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_stack_layout_manager.*

class StackLayoutManagerActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_stack_layout_manager
    }

    override fun listener() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCardRecyclerView.layoutManager = StackLayoutManager()

        var mData: MutableList<String> = mutableListOf()
        for (i in 0..10) {
            mData.add("llll $i")
        }
        var mAdapter = ImageListAdapter(mData)
        mCardRecyclerView.adapter = mAdapter
        initPtrFrameLayout()
    }

    private fun initPtrFrameLayout() {


        mPtrFrameLayout.disableWhenHorizontalMove(true)
      //  val header = MaterialHeader(this)
       // val header = PtrClassicDefaultHeader(this)
        val header = PtrEventHeader(this)
        header.setPadding(0, 30, 0, 30)
        mPtrFrameLayout.headerView = header
        mPtrFrameLayout.addPtrUIHandler(header)
        mPtrFrameLayout.isPullToRefresh=false
        mPtrFrameLayout.isKeepHeaderWhenRefresh = true

        mPtrFrameLayout.setPtrHandler(object : PtrHandler{
            override fun onRefreshBegin(frame: PtrFrameLayout?) {

              Toast.makeText(mContext,"开始",Toast.LENGTH_SHORT).show()
                mPtrFrameLayout.postDelayed({
                    frame!!.refreshComplete()
                    Toast.makeText(mContext,"结束",Toast.LENGTH_SHORT).show()
                },200)
            }

            override fun checkCanDoRefresh(frame: PtrFrameLayout?, content: View?, header: View?): Boolean {
                // 默认实现，根据实际情况做改动
                Log.e("StackLayoutManagerActivity","checkCanDoRefresh")
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header)
            }
        })


        mPtrFrameLayout.postDelayed({
            mPtrFrameLayout.autoRefresh(true)
        },100)



    }
}
