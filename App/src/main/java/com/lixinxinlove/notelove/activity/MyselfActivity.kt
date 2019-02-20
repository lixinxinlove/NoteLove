package com.lixinxinlove.notelove.activity

import android.os.Bundle
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.R
import kotlinx.android.synthetic.main.activity_myself.*

class MyselfActivity : BaseActivity() {
    override fun layoutId(): Int {
        return R.layout.activity_myself
    }

    override fun listener() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
