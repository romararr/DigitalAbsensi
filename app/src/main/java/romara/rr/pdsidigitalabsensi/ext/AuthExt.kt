package romara.rr.pdsidigitalabsensi.ext

import android.app.Activity
import android.content.Context
import org.jetbrains.anko.startActivity
import romara.rr.pdsidigitalabsensi.view.LoginActivity

fun Context.onLogout(context: Activity) {
    spClearTime()
    spClear()

    startActivity<LoginActivity>()
    context.finish()
}