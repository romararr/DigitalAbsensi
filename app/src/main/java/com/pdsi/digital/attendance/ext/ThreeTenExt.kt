package com.pdsi.digital.attendance.ext

import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import java.text.SimpleDateFormat
import java.util.*

fun Context.initTime(){
    AndroidThreeTen.init(this);
}

fun getMillis(date: String): Long {
    val d: Calendar = parseDate(date)
    val ms = d.timeInMillis

    return ms
}

fun parseDate(date: String): Calendar {
    val d: Date = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date)
    val c = Calendar.getInstance()

    c.setTimeInMillis(d.time)
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)
    return c
}