package romara.rr.pdsidigitalabsensi.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romara.rr.pdsidigitalabsensi.api.API
import romara.rr.pdsidigitalabsensi.constants.ConstVar
import romara.rr.pdsidigitalabsensi.constants.Constant
import romara.rr.pdsidigitalabsensi.ext.spGetLocation
import romara.rr.pdsidigitalabsensi.ext.spGetUser
import romara.rr.pdsidigitalabsensi.interfaces.home.iHome
import romara.rr.pdsidigitalabsensi.model.location.MLocation
import romara.rr.pdsidigitalabsensi.model.user.MUser
import romara.rr.pdsidigitalabsensi.view.HomeActivity
import java.util.*


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
                                iHome.onDataCompleteFromApi(Constant.emptyAttendRes(), "", "")
                                return
                            }
                            302 -> {
                                context.toast("Anda Sudah Absen")
                                iHome.onDataCompleteFromApi(Constant.emptyAttendRes(), ConstVar.RETURN, ConstVar.SUDAHABSEN)
                                return
                            }
                        }

                        if (response.body()!!.message.equals(ConstVar.OUTOFFICE) ||
                                response.body()!!.message.equals(ConstVar.MANUAL)
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
    fun onAttendOutOffice(context: Context, condition: String, note: String = "out office attend") {
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

    fun getTimeOnLogin(context: Context) {
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", context.spGetUser())

        API.create(context).getUserDetail(requestBody)
                .enqueue(object : Callback<MUser> {
                    override fun onFailure(call: Call<MUser>, t: Throwable) {
                        iHome.onDataErrorFromApi(t)
                    }

                    override fun onResponse(call: Call<MUser>, response: Response<MUser>) {
                        iHome.onGetAbsenCompleteApi(response.body() as MUser)
                        Log.d("ATTEND FIRST DATA", response.body().toString())
                    }
                })
    }

    fun parseTime(time: String): String {
//        return LocalTime.parse(time, DateTimeFormatter.ISO_TIME).toString()
        val t = time.split(".")[0].split(":")
        return t[0] + ":" + t[1]
    }

}