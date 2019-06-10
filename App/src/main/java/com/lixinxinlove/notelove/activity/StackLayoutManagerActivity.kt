package com.lixinxinlove.notelove.activity

import android.os.Bundle
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

        val mData: MutableList<String> = mutableListOf()
        for (i in 0..10) {
            mData.add("llll $i")
        }
        val mAdapter = ImageListAdapter(mData)
        mCardRecyclerView.adapter = mAdapter
    }


}
