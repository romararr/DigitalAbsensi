package romara.rr.pdsidigitalabsensi.ext

import android.view.View

fun View.gone(): View {
    this.visibility = View.GONE
    return this
}

fun View.visible(): View {
    this.visibility = View.VISIBLE
    return this
}