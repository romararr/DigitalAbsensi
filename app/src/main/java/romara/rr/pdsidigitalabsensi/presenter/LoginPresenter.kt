package romara.rr.pdsidigitalabsensi.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romara.rr.pdsidigitalabsensi.api.API
import romara.rr.pdsidigitalabsensi.interfaces.login.iLogin
import romara.rr.pdsidigitalabsensi.model.User.MLogin

class LoginPresenter(context: Context) {

    val iLogin = context as iLogin

    fun onLogin(context: Context, username: String, password: String) {

        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", username)
        requestBody.put("password", password)

        API.create(context).login(requestBody)
            .enqueue(object : Callback<MLogin> {
                override fun onFailure(call: Call<MLogin>, t: Throwable) {
                    iLogin.onDataErrorFromApi(t)
                }

                override fun onResponse(call: Call<MLogin>, response: Response<MLogin>) {
                    if (response.body()?.status == true) iLogin.onDataCompleteFromApi(response.body() as MLogin)
                    else (Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show())
                    Log.d("LOGINDATA", response.body().toString())
                }
            })

    }
}