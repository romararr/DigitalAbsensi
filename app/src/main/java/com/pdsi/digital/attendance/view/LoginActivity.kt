package com.pdsi.digital.attendance.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_layout.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast
import com.pdsi.digital.attendance.R
import com.pdsi.digital.attendance.base.BaseActivity
import com.pdsi.digital.attendance.ext.*
import com.pdsi.digital.attendance.interfaces.login.iLogin
import com.pdsi.digital.attendance.local.SharedPrefManager
import com.pdsi.digital.attendance.model.user.MUserLogin
import com.pdsi.digital.attendance.presenter.LoginPresenter

class LoginActivity : BaseActivity(), iLogin {

    private val presenter by lazy {
        LoginPresenter(this)
    }

    var username: String = ""
    var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        // Init
        initTime()
        presenter

        // Event
        login_button.setOnClickListener {
            onLoginCheck()
//            dummyLogin()
        }

    }

    private fun onLoginCheck() {
        username = input_username.text.toString()
        password = input_password.text.toString()

        if (username.isEmpty()) {
            input_username.error = "Username kosong!"
            return
        }

        if (password.isEmpty()) {
            input_password.error = "Password kosong!"
            return
        }

        onLoading(true)
        presenter.onLogin(this, username, password)
    }

    private fun dummyLogin() {
        spSetToday()
        startActivity(intentFor<HomeActivity>().newTask())
    }

    override fun onLoading(status: Boolean?) {
        if (status == true){
            loading.visible()
            login_button.gone()
        } else {
            loading.gone()
            login_button.visible()
        }
    }

    override fun onDataCompleteFromApi(q: MUserLogin) {

        spSetToken(q.token)
        spSetUserdata(q.data.username)
        spSetNip(q.data.nip)
        spSetRole(q.data.role)
        spSetPosid(q.data.posid.toString())
        spSetToday()

        startActivity(intentFor<HomeActivity>().newTask())
    }

    override fun onDataErrorFromApi(throwable: Throwable) {
        onLoading()
        toast("Network Error")
        Log.d("ERR API", throwable.toString())
    }

    override fun onStart() {
        super.onStart()
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(intentFor<HomeActivity>().newTask())
        }
    }

}