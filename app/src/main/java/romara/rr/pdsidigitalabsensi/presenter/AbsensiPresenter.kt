package romara.rr.pdsidigitalabsensi.presenter

import android.os.Build
import androidx.annotation.RequiresApi
import romara.rr.pdsidigitalabsensi.interfaces.absensi.iAbsensi
import java.time.LocalDateTime

class AbsensiPresenter(absenView: iAbsensi) {

    val v = absenView

    // Default Value
    @RequiresApi(Build.VERSION_CODES.O)
    val date = LocalDateTime.now()

    @RequiresApi(Build.VERSION_CODES.O)
    val bulan = date.month

    @RequiresApi(Build.VERSION_CODES.O)
    val day = date.dayOfMonth

    @RequiresApi(Build.VERSION_CODES.O)
    val from = "" + date.year + "-" + bulan + date.dayOfMonth

    @RequiresApi(Build.VERSION_CODES.O)
    val to = "" + date.year + "-" + bulan + day

    val page = 1
    val limit = 5

//    fun getData(context: Context, )

}