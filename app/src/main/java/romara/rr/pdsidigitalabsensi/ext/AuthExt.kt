package romara.rr.pdsidigitalabsensi.ext

import android.app.Activity
import android.content.Context
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import romara.rr.pdsidigitalabsensi.view.LoginActivity

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