package romara.rr.pdsidigitalabsensi.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romara.rr.pdsidigitalabsensi.api.API
import romara.rr.pdsidigitalabsensi.ext.spGetToken
import romara.rr.pdsidigitalabsensi.ext.spGetUser
import romara.rr.pdsidigitalabsensi.interfaces.login.iLogs
import romara.rr.pdsidigitalabsensi.model.Location.MLocation

class LogPresenter(context: Context) {

    val iLogs = context as iLogs

    fun onGetData(context: Context) {
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", context.spGetUser())

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