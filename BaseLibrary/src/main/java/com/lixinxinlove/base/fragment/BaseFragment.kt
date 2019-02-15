package com.lixinxinlove.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.widgets.ProgressLoading

/**
 * 基类
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {

    lateinit var mContext: Context

    lateinit var rootView: View

    abstract fun layoutId(): Int
    abstract fun listener()
    abstract fun findView()
    abstract fun _onCreateView()
    lateinit var mProgressLoading: ProgressLoading

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(layoutId(), container, false)
        mProgressLoading = ProgressLoading.create(mContext)
        findView()
        _onCreateView()
        listener()
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

}