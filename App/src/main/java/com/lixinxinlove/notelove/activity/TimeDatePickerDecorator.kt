package com.squareup.timessquare

import android.graphics.Color
import java.util.*


class TimeDatePickerDecorator : CalendarCellDecorator {

    override fun decorate(cellView: CalendarCellView, date: Date) {

        if (cellView.isSelectable) {   //可以选择的日期
            if (cellView.isSelected) {   //被选择的日期
                when {
                    cellView.rangeState == RangeState.FIRST -> {      //被选择的日期开始
                        cellView.dayOfMonthTextView.setTextColor(Color.RED)
                    }
                    cellView.rangeState == RangeState.LAST -> {  //被选择的日期结束的位置
                        cellView.dayOfMonthTextView.setTextColor(Color.RED)
                    }
                    cellView.rangeState == RangeState.MIDDLE -> {  //被选择的日期中间的位置
                        cellView.dayOfMonthTextView.setTextColor(Color.YELLOW)
                    }
                    cellView.rangeState == RangeState.NONE -> {   ////被选择的日期单选的位置
                        cellView.dayOfMonthTextView.setTextColor(Color.RED)
                    }
                }
            } else {
                cellView.dayOfMonthTextView.setTextColor(Color.parseColor("#333333"))
            }
        } else {
            if (!cellView.isCurrentMonth) {   //日期是本月的
                cellView.dayOfMonthTextView.text = ""
            }
            cellView.setBackgroundColor(Color.WHITE)
        }
    }
}
