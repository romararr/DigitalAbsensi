package romara.rr.pdsidigitalabsensi.presenter

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.jetbrains.anko.toast
import romara.rr.pdsidigitalabsensi.interfaces.home.iHome
import romara.rr.pdsidigitalabsensi.view.HomeActivity
import java.time.LocalDateTime
import kotlin.math.abs

class HomePresenter(context: Context) {

    val iHome = context as iHome

    fun onClickAbsen(context: Context, username: String) {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDifferentTime(come: String): Int {

        var diff = 0
        var hDiff = 0
        var mDiff = 0

        val hNow = Integer.parseInt(LocalDateTime.now().hour.toString())
        val mNow = Integer.parseInt(LocalDateTime.now().minute.toString())

        var hCome = Integer.parseInt(LocalDateTime.parse(come).hour.toString())
        var mCome = Integer.parseInt(LocalDateTime.parse(come).minute.toString())

        if (mNow > mCome) {
            --hCome
            mCome += 60
        }

        mDiff = mCome - mNow
        hDiff = (hCome - hNow) * 60
        diff = (hDiff + mDiff) * 60 * 1000

        return abs(diff)

    }


}