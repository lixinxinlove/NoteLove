package com.lixinxinlove.notelove.activity

import android.os.Bundle
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.R
import kotlinx.android.synthetic.main.activity_select_time.*
import java.util.*


/**
 * 选择时间
 */
class SelectTimeActivity : BaseActivity() {


    override fun layoutId(): Int {
        return R.layout.activity_select_time
    }


    override fun listener() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nextYear = Calendar.getInstance()
        val today = Date()
        nextYear.add(Calendar.YEAR, 1)
        calendarView.init(today, nextYear.time).withSelectedDate(today)
    }
}
