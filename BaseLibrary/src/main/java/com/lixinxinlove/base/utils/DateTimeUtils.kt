package com.lixinxinlove.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    val HH_mm = "HH:mm"
    val HH_mm_ss = "HH:mm:ss"
    val MM_Yue_dd_Ri = "MM月dd日"
    val MM_yy = "MM/yy"
    val M_Yue_d_Ri = "M月d日"
    val ONE_DAY = 86400000L
    val ONE_HOUR = 3600000L
    val ONE_MINUTE = 60000L
    val ONE_SECOND = 1000L
    private val PATTERNS = arrayOf("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyyMMdd")
    val dd_MM = "dd/MM"
    var hasServerTime = false
    var tslgapm = 0L
    var tss: String? = null
    private val weekdays = arrayOf("", "周日", "周一", "周二", "周三", "周四", "周五", "周六")
    private val weekdays1 = arrayOf("", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
    val yyyyMMdd = "yyyyMMdd"
    val yyyyMMddHHmmss = "yyyyMMddHHmmss"
    val yyyy_MM = "yyyy-MM"
    val yyyy_MM_dd = "yyyy-MM-dd"
    val yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm"
    val yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"
    val yyyy_Nian_MM_Yue_dd_Ri = "yyyy年MM月dd日"


    val currentDateTime: Calendar
        get() {
            val localCalendar = Calendar.getInstance()
            localCalendar.isLenient = false
            if (hasServerTime)
                localCalendar.timeInMillis = localCalendar.timeInMillis + tslgapm
            return localCalendar
        }


    /**
     * 时间戳
     *
     * @return
     */
    val timeStamp: Long
        get() = System.currentTimeMillis()

    val todayString: String
        get() {
            val time = timeStamp
            return DateTimeUtils.timeForDate(time, yyyy_MM_dd)
        }

    val toadyCalendar: Calendar
        get() {
            val calendar = Calendar.getInstance()
            val startDate = Date(timeStamp)
            calendar.time = startDate
            return calendar
        }

    /**
     * 获得当天整点的时间戳
     *
     * @return
     */
    val timesnight: Long
        get() {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 24)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.timeInMillis / 1000 - 60 * 60 * 24
        }

    /**
     * 获得指定时间的时间戳
     *
     * @param dateString
     * @param format     yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    fun getTime(dateString: String, format: String): Long {
        val df = SimpleDateFormat(format)
        val date: Date
        try {
            date = df.parse(dateString)
            return date.time / 1000
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    fun getDays(date1: String?, date2: String?): Long {
        if (date1 == null || date1 == "")
            return 0
        if (date2 == null || date2 == "")
            return 0
        // 转换为标准时间
        val myFormatter = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        var mydate: Date? = null
        try {
            date = myFormatter.parse(date1)
            mydate = myFormatter.parse(date2)
        } catch (e: Exception) {
        }

        return (date!!.time - mydate!!.time) / (24 * 60 * 60 * 1000)
    }

    /**
     * 两个时间之间的天数 ps 毫秒
     *
     * @param date1
     * @param date2
     * @return
     */
    fun getDays(date1: Long, date2: Long): Long {
        if (date1 == 0L)
            return 0
        return if (date2 == 0L) 0 else (date2 - date1) / (24 * 60 * 60 * 1000)
        // 转换为标准时间

    }

    // 获取当天时间
    fun getNowTime(dateformat: String): String {
        val now = Date()
        val dateFormat = SimpleDateFormat(dateformat)// 可以方便地修改日期格式
        return dateFormat.format(now)
    }


    /**
     * 精确到秒
     *
     * @param nowTime
     * @param day
     * @return
     */
    fun calculationDateTemp(nowTime: Long, day: Int): Long {
        return nowTime + day * ONE_DAY / 1000
    }

    /**
     * 根据时间戳格式化日期
     *
     * @param time
     * @return
     */
    fun timeForDate(time: Long, format: String): String {
        val date = Date(time)
        val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }

    /*
     * 秒转化
     */
    fun formatTime(ms: Long): String {
        var result = ""
        val mi = 60
        val hh = mi * 60
        val dd = hh * 24

        val day = ms / dd
        val hour = (ms - day * dd) / hh
        val minute = (ms - day * dd - hour * hh) / mi
        val second = ms - day * dd - hour * hh - minute * mi
        if (day > 0) {
            result = day.toString() + "天" + hour + "小时" + minute + "分" + second + "秒"
        } else if (hour > 0) {
            result = hour.toString() + "小时" + minute + "分" + second + "秒"
        } else if (minute > 0) {
            result = minute.toString() + "分" + second + "秒"
        } else if (second > 0) {
            result = second.toString() + "秒"
        }
        return result
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return
     */
    fun getWeekOfDate(date: Long): String {
        val weekDaysName = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        val calendar = Calendar.getInstance()
        calendar.time = Date(date)
        val intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        return weekDaysName[intWeek]
    }

    fun getMonthAndDays(date: Long): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date(date)
        val dom = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        return month.toString() + "月" + dom
    }


}