package com.pdsi.digital.attendance.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.pdsi.digital.attendance.api.API
import com.pdsi.digital.attendance.interfaces.login.iLogin
import com.pdsi.digital.attendance.model.user.MUserLogin
import com.pdsi.digital.attendance.view.LoginActivity

class LoginPresenter(context: Context) {

    val iLogin = context as iLogin

    fun onLogin(context: Context, username: String, password: String) {

        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody["username"] = username
        requestBody.put("password", password)

        API.create(context).login(requestBody)
                .enqueue(object : Callback<MUserLogin> {
                    override fun onFailure(call: Call<MUserLogin>, t: Throwable) {
                        iLogin.onDataErrorFromApi(t)
                        t.printStackTrace();
                    }

                    override fun onResponse(call: Call<MUserLogin>, response: Response<MUserLogin>) {
                        Log.d("LOGINDATA", response.body().toString())
                        iLogin.onLoading()

                        if (response.body()?.status == true) iLogin.onDataCompleteFromApi(response.body() as MUserLogin)
                        else Toast.makeText(context, "Username atau Password tidak sesuai", Toast.LENGTH_SHORT).show()

                    }
                })

    }
}