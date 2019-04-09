package com.lixinxinlove.base.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.base.common.AppManager
import com.kotlin.base.widgets.ProgressLoading

/**
 * 基类
 */
abstract class BaseActivity : AppCompatActivity() {

    var mContext: Context=this
    abstract fun layoutId(): Int
    abstract fun listener()
    lateinit var mProgressLoading: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
        mContext = this
        setContentView(layoutId())
        listener()
        mProgressLoading = ProgressLoading.create(mContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }


}