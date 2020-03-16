package com.pdsi.digital.attendance.ext

import android.content.Context
import android.os.Build
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import com.pdsi.digital.attendance.local.SharedPrefManager
import java.time.LocalDateTime

private fun pref(context: Context): SharedPrefManager {
    return SharedPrefManager.getInstance(context)
}

// Token
fun Context.spGetToken(): String {
    return pref(this).getToken().toString()
}

fun Context.spSetToken(data: String) {
    pref(this).saveToken(data)
}

// POS ID
fun Context.spGetPosid(): String {
    return pref(this).getPosId().toString()
}

fun Context.spSetPosid(data: String) {
    pref(this).savePosId(data)
}

// Role
fun Context.spGetRole(): String {
    return pref(this).getRole().toString()
}

fun Context.spSetRole(data: String) {
    pref(this).saveRole(data)
}

// Userdata
fun Context.spGetUser(): String {
    return pref(this).getUser().toString()
}

fun Context.spSetUserdata(data: String) {
    pref(this).saveUser(data)
}

// NIP
fun Context.spGetNip(): String {
    return pref(this).getNip().toString()
}

fun Context.spSetNip(data: String) {
    pref(this).saveNip(data)
}

// Today Date
fun Context.spGetToday(): Boolean {
    return pref(this).getToday()
}

fun Context.spSetToday() {
    pref(this).saveDateNow(LocalDate.now().dayOfMonth)
}

// Time Absen Masuk
fun Context.spGetTimeCome(): String {
    return pref(this).getTimeIn().toString()
}

fun Context.spSetTimeCome(time: String = "") {
    if (time.isEmpty()) pref(this).saveTimeIn(LocalTime.now().toString())
    else pref(this).saveTimeIn(time)
}

// Time Absen Pulang
fun Context.spGetTimeOut(): String {
    return pref(this).getTimeOut().toString()
}

fun Context.spSetTimeOut(time: String = "") {
    if (time.isEmpty()) pref(this).saveTimeOut(LocalTime.now().toString())
    else pref(this).saveTimeOut(time)
}

// Time Break
fun Context.spGetBreak(): String {
    return pref(this).getBreak().toString()
}

fun Context.spSetBreak(time: String = "") {
    if (time.isEmpty()) pref(this).saveBreak(LocalTime.now().toString())
    else pref(this).saveBreak(time)
}

// Time End Break
fun Context.spGetEndBreak(): String {
    return pref(this).getEndBreak().toString()
}

fun Context.spSetEndBreak(time: String = "") {
    if (time.isEmpty()) pref(this).saveEndBreak(LocalTime.now().toString())
    else pref(this).saveEndBreak(time)
}

// Location
fun Context.spGetLocation(key: String): String {
    return pref(this).getLocation(key).toString()
}

fun Context.spSetLocation(lat: Double, long: Double) {
    pref(this).saveLocation("latitude", lat)
    pref(this).saveLocation("longitude", long)
}

// Clear Time
fun Context.spClearTime() {
    pref(this).clearRecentTime()
}

// Clear Token
fun Context.spClear() {
    pref(this).clear()
}