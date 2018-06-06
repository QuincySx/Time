package com.smallraw.time.utils

import android.content.Context
import java.util.*

fun getWeekOfDate(context: Context, dt: Date): String {
    val weekDays = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
    val cal = Calendar.getInstance()
    cal.setTime(dt)

    var w = cal.get(Calendar.DAY_OF_WEEK) - 1
    if (w < 0)
        w = 0

    return weekDays[w]
}