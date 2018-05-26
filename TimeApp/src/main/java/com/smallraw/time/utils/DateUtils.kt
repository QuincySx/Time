package com.smallraw.time.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private val df = object : ThreadLocal<DateFormat>() {
    override fun initialValue(): DateFormat {
        return SimpleDateFormat("yyyy-MM-dd")
    }
}

fun dateFormat(date: Date): String {
    return df.get().format(date)
}

fun dateParse(date: String): Date {
    return df.get().parse(date)
}