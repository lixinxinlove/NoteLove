package com.squareup.timessquare

import android.graphics.Color
import android.util.Log
import java.util.*


class TimeDatePickerDecorator : CalendarCellDecorator {

    override fun decorate(cellView: CalendarCellView, date: Date) {

        if (cellView.isSelectable) {
            val json = cellView.getTag().toString()
            Log.e("TimeDatePickerDecorator", json)
            cellView.setBackgroundColor(Color.WHITE)
            if (cellView.isSelected) {
                cellView.dayOfMonthTextView.setTextColor(Color.RED)
                if (cellView.rangeState == RangeState.FIRST) {

                } else if (cellView.rangeState == RangeState.LAST) {

                } else if (cellView.rangeState == RangeState.MIDDLE) {

                } else {

                }
            } else {
                cellView.dayOfMonthTextView.setTextColor(Color.parseColor("#333333"))
            }
        } else {
            cellView.setBackgroundColor(Color.parseColor("#999999"))
        }
    }
}
