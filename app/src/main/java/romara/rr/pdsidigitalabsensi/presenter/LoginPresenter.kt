package romara.rr.pdsidigitalabsensi.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romara.rr.pdsidigitalabsensi.api.API
import romara.rr.pdsidigitalabsensi.interfaces.login.iLogin
import romara.rr.pdsidigitalabsensi.model.user.MUserLogin
import romara.rr.pdsidigitalabsensi.view.LoginActivity

class LoginPresenter(context: Context) {

    val iLogin = context as iLogin

    fun onLogin(context: Context, username: String, password: String) {

        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", username)
        requestBody.put("password", password)

        API.create(context).login(requestBody)
                .enqueue(object : Callback<MUserLogin> {
                    override fun onFailure(call: Call<MUserLogin>, t: Throwable) {
                        iLogin.onDataErrorFromApi(t)
                        t.printStackTrace();
                    }

                    override fun onResponse(call: Call<MUserLogin>, response: Response<MUserLogin>) {
                        Log.d("LOGINDATA", response.body().toString())

                        if (response.body()?.status == true) iLogin.onDataCompleteFromApi(response.body() as MUserLogin)
                        else {
                            iLogin.onLoading()
                            (Toast.makeText(context, "Username atau Password tidak sesuai", Toast.LENGTH_SHORT).show())
                        }
                    }
                })

    }
}