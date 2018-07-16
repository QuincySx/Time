package com.smallraw.time.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private val df = object : ThreadLocal<DateFormat>() {
    override fun initialValue(): DateFormat {
        return SimpleDateFormat("yyyy-MM-dd")
    }
}

private val df1 = object : ThreadLocal<DateFormat>() {
    override fun initialValue(): DateFormat {
        return SimpleDateFormat("MM月dd日")
    }
}

fun dateFormat(date: Date): String {
    return df.get().format(date)
}

fun monthDayFormat(date: Date): String {
    return df1.get().format(date)
}

fun dateParse(date: String): Date {
    return df.get().parse(date)
}

fun differentDays(date1: Date, date2: Date): Int {
    val cal1 = Calendar.getInstance()
    cal1.time = date1

    val cal2 = Calendar.getInstance()
    cal2.time = date2
    val day1 = cal1.get(Calendar.DAY_OF_YEAR)
    val day2 = cal2.get(Calendar.DAY_OF_YEAR)

    val year1 = cal1.get(Calendar.YEAR)
    val year2 = cal2.get(Calendar.YEAR)
    if (year1 != year2) {
        //同一年
        var timeDistance = 0
        for (i in year1 until year2) {
            if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                //闰年
                timeDistance += 366
            } else {
                //不是闰年
                timeDistance += 365
            }
        }

        return timeDistance + (day2 - day1)
    } else {
        //不同年
        return day2 - day1
    }
}