package romara.rr.pdsidigitalabsensi.presenter

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romara.rr.pdsidigitalabsensi.api.API
import romara.rr.pdsidigitalabsensi.ext.spGetUser
import romara.rr.pdsidigitalabsensi.interfaces.login.iLogs
import romara.rr.pdsidigitalabsensi.model.location.MLocation

class LogPresenter(context: Context) {

    val iLogs = context as iLogs

    fun onGetData(context: Context, to: String? = "", from: String? = "", page: String? = "1") {
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", context.spGetUser())
        requestBody.put("from", from.toString())
        requestBody.put("to", to.toString())
        requestBody.put("page", page.toString())
        requestBody.put("limit", "30")

        API.create(context).logAbsen(requestBody)
            .enqueue(object : Callback<MLocation> {
                override fun onFailure(call: Call<MLocation>, t: Throwable) {
                    iLogs.onDataErrorFromApi(t)
                }

                override fun onResponse(call: Call<MLocation>, response: Response<MLocation>) {
                    iLogs.onDataCompleteFromApi(response.body() as MLocation)
                    Log.d("LOGS DATA", response.body().toString())
                }
            })

    }

}