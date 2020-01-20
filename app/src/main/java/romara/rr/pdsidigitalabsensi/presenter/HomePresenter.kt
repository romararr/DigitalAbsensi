package romara.rr.pdsidigitalabsensi.presenter

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romara.rr.pdsidigitalabsensi.api.API
import romara.rr.pdsidigitalabsensi.ext.spGetLocation
import romara.rr.pdsidigitalabsensi.ext.spGetUser
import romara.rr.pdsidigitalabsensi.interfaces.home.iHome
import romara.rr.pdsidigitalabsensi.model.Location.MLocation
import java.time.LocalDateTime
import kotlin.math.abs

class HomePresenter(context: Context) {

    val iHome = context as iHome

    fun onAttend(context: Context) {
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", context.spGetUser())
        requestBody.put("condition", "come")
        requestBody.put("note", "datang")
        requestBody.put("latitude", context.spGetLocation("latitude"))
        requestBody.put("longitude", context.spGetLocation("longitude"))

        API.create(context).attend(requestBody)
            .enqueue(object : Callback<MLocation> {
                override fun onFailure(call: Call<MLocation>, t: Throwable) {
                    iHome.onDataErrorFromApi(t)
                }

                override fun onResponse(call: Call<MLocation>, response: Response<MLocation>) {
                    iHome.onDataCompleteFromApi(response.body() as MLocation)
                    Log.d("ATTEND DATA", response.body().toString())
                }
            })
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