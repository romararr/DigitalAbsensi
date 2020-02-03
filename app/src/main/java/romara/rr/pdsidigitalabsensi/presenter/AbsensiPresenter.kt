package romara.rr.pdsidigitalabsensi.presenter

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romara.rr.pdsidigitalabsensi.api.API
import romara.rr.pdsidigitalabsensi.ext.spGetUser
import romara.rr.pdsidigitalabsensi.interfaces.absensi.iAbsensi
import romara.rr.pdsidigitalabsensi.model.absen.MAttend
import java.text.SimpleDateFormat
import java.util.*

class AbsensiPresenter(context: Context) {

    val iAbsen = context as iAbsensi

    fun onGetData(context: Context, to: String? = "", from: String? = "", page: String? = "1") {
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", context.spGetUser())
        requestBody.put("from", from.toString())
        requestBody.put("to", to.toString())
        requestBody.put("page", page.toString())
        requestBody.put("limit", "100")


        API.create(context).getPersonalAbsen(requestBody)
            .enqueue(object : Callback<MAttend> {
                override fun onFailure(call: Call<MAttend>, t: Throwable) {
                    iAbsen.onDataErrorFromApi(t)
                }

                override fun onResponse(call: Call<MAttend>, response: Response<MAttend>) {
                    iAbsen.onDataCompleteFromApi(response.body() as MAttend)
                    Log.d("ATTEND LIST DATA", response.body().toString())
                }
            })

    }

}