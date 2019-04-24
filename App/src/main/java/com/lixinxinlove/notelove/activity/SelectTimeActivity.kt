package com.lixinxinlove.notelove.activity

import android.os.Bundle
import android.util.Log
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.base.utils.DateTimeUtils
import com.lixinxinlove.notelove.R
import com.squareup.timessquare.CalendarCellDecorator
import com.squareup.timessquare.CalendarPickerView
import kotlinx.android.synthetic.main.activity_select_time.*
import java.util.*


/**
 * 选择时间
 */
class SelectTimeActivity : BaseActivity(), CalendarPickerView.CellClickInterceptor,
    CalendarPickerView.OnDateSelectedListener, CalendarPickerView.OnInvalidDateSelectedListener {


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
        calendarView.init(today, nextYear.time)
            .withSelectedDate(today)
            .inMode(CalendarPickerView.SelectionMode.RANGE)

        calendarView.setCellClickInterceptor(this)
        calendarView.setOnDateSelectedListener(this)
        calendarView.setOnInvalidDateSelectedListener(this)

        val decorator = TimeDatePickerDecorator()
        val d = ArrayList<CalendarCellDecorator>()
        d.add(decorator)
        calendarView.decorators=d


    }

    override fun onCellClicked(date: Date?): Boolean {
        Log.e("onCellClicked", DateTimeUtils.timeForDate(date!!.time, DateTimeUtils.yyyy_MM_dd_HH_mm_ss))
        return false
    }

    override fun onDateSelected(date: Date?) {
        Log.e("onDateSelected", DateTimeUtils.timeForDate(date!!.time, DateTimeUtils.yyyy_MM_dd_HH_mm_ss))
    }

    override fun onDateUnselected(date: Date?) {
        Log.e("onDateUnselected", DateTimeUtils.timeForDate(date!!.time, DateTimeUtils.yyyy_MM_dd_HH_mm_ss))
    }

    //选择无效日期
    override fun onInvalidDateSelected(date: Date?) {
        Log.e("onInvalidDateSelected", DateTimeUtils.timeForDate(date!!.time, DateTimeUtils.yyyy_MM_dd_HH_mm_ss))
    }


}
