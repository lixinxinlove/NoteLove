package com.squareup.timessquare

import android.graphics.Color
import android.util.Log
import java.util.*


class TimeDatePickerDecorator : CalendarCellDecorator {

    override fun decorate(cellView: CalendarCellView, date: Date) {

        if (cellView.isSelectable) {
            val json = cellView.getTag().toString()
            Log.e("TimeDatePickerDecorator", json)
            if (cellView.isSelected) {
                cellView.dayOfMonthTextView.setTextColor(Color.RED)
                when {
                    cellView.rangeState == RangeState.FIRST -> {
                        cellView.dayOfMonthTextView.setTextColor(Color.RED)
                    }
                    cellView.rangeState == RangeState.LAST -> {
                        cellView.dayOfMonthTextView.setTextColor(Color.RED)
                    }
                    cellView.rangeState == RangeState.MIDDLE -> {
                        cellView.dayOfMonthTextView.setTextColor(Color.YELLOW)
                    }
                    cellView.rangeState == RangeState.NONE -> {
                        cellView.dayOfMonthTextView.setTextColor(Color.RED)
                    }
                }
            } else {
                cellView.dayOfMonthTextView.setTextColor(Color.parseColor("#333333"))
            }
        } else {
            if (!cellView.isCurrentMonth) {
                cellView.dayOfMonthTextView.text = ""
            }
            cellView.setBackgroundColor(Color.WHITE)
        }
    }
}
