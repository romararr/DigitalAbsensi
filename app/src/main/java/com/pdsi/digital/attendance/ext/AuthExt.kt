package com.pdsi.digital.attendance.ext

import android.app.Activity
import android.content.Context
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import com.pdsi.digital.attendance.view.LoginActivity

fun Context.onLogout(context: Activity) {
    spClearTime()
    spClear()

    startActivity<LoginActivity>()
    context.finish()
}

fun Context.onExpired(context: Activity){
    toast("Silahkan Login kembali")
    onLogout(context)
}