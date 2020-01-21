package romara.rr.pdsidigitalabsensi.presenter

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romara.rr.pdsidigitalabsensi.api.API
import romara.rr.pdsidigitalabsensi.constants.ConstVar
import romara.rr.pdsidigitalabsensi.ext.spGetLocation
import romara.rr.pdsidigitalabsensi.ext.spGetUser
import romara.rr.pdsidigitalabsensi.interfaces.home.iHome
import romara.rr.pdsidigitalabsensi.model.Location.MLocation
import romara.rr.pdsidigitalabsensi.view.HomeActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

class HomePresenter(context: Context) {

    val iHome = context as iHome

    // Set Attend Request Body
    fun attendBody(
        context: Context,
        condition: String,
        note: String = "note"
    ): MutableMap<String, String> {

        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", context.spGetUser())
        requestBody.put("condition", condition)
        requestBody.put("note", note)
        requestBody.put("latitude", context.spGetLocation("latitude"))
        requestBody.put("longitude", context.spGetLocation("longitude"))

        return requestBody
    }

    // Attend in Office
    fun onAttend(context: Context, condition: String, note: String) {

        API.create(context).attend(attendBody(context, condition, note))
            .enqueue(object : Callback<MLocation> {
                override fun onFailure(call: Call<MLocation>, t: Throwable) {
                    iHome.onDataErrorFromApi(t)
                }

                override fun onResponse(call: Call<MLocation>, response: Response<MLocation>) {
                    when (response.code()) {
                        301 -> {
                            context.longToast("Error, Hubungi servicedesk dengan menyebutkan kode kesalahan berikut: EC002")
                            return
                        }
                        302 -> {
                            context.toast("Anda Sudah Absen")
                            return
                        }
                    }

                    if (response.body()?.message.equals(ConstVar.OUTOFFICE) ||
                        response.body()?.message.equals(ConstVar.MANUAL)
                    ) {
                        HomeActivity.instance?.showNoteDialog(condition)
                    } else {
                        iHome.onDataCompleteFromApi(
                            response.body() as MLocation,
                            condition,
                            response.body()?.condition.toString()
                        )
                    }

                    Log.d("ATTEND DATA", response.body().toString())
                }
            })
    }

    // Attend Out of Office
    fun onAttendOutOffice(context: Context, condition: String, note: String = "out office come") {
        API.create(context).attendOutOffice(attendBody(context, condition, note))
            .enqueue(object : Callback<MLocation> {
                override fun onFailure(call: Call<MLocation>, t: Throwable) {
                    iHome.onDataErrorFromApi(t)
                }

                override fun onResponse(call: Call<MLocation>, response: Response<MLocation>) {
                    iHome.onDataCompleteFromApi(
                        response.body() as MLocation,
                        condition,
                        response.body()?.condition.toString()
                    )
                    Log.d("OUT OFFICE ATTEND DATA", response.body().toString())
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseTime(time: String): String {
        return DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.parse(time))
    }

}