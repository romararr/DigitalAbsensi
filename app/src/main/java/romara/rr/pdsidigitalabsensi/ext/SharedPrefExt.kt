package romara.rr.pdsidigitalabsensi.ext

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import romara.rr.pdsidigitalabsensi.local.SharedPrefManager
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

// Userdata
fun Context.spGetUser(): String {
    return pref(this).getUser().toString()
}

fun Context.spSetUserdata(data: String) {
    pref(this).saveUser(data)
}

// Today Date
@RequiresApi(Build.VERSION_CODES.O)
fun Context.spGetToday(): Boolean? {
    return pref(this).getToday()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.spSetToday() {
    pref(this).saveDateNow(LocalDateTime.now().dayOfMonth)
}

// Time Absen Masuk
fun Context.spGetTimeCome(): String {
    return pref(this).getTimeIn().toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.spSetTimeCome() {
    pref(this).saveTimeIn(LocalDateTime.now().toString())
}

// Time Absen Pulang
fun Context.spGetTimeOut(): String {
    return pref(this).getTimeOut().toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.spSetTimeOut() {
    pref(this).saveTimeOut(LocalDateTime.now().toString())
}

// Time Break
fun Context.spGetBreak(): String {
    return pref(this).getBreak().toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.spSetBreak() {
    pref(this).saveBreak(LocalDateTime.now().toString())
}

// Time End Break
fun Context.spGetEndBreak(): String {
    return pref(this).getEndBreak().toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.spSetEndBreak() {
    pref(this).saveEndBreak(LocalDateTime.now().toString())
}

// Clear Time
@RequiresApi(Build.VERSION_CODES.O)
fun Context.spClearTime() {
    pref(this).clearRecentTime()
}

// Clear Token
fun Context.spClear() {
    pref(this).clear()
}