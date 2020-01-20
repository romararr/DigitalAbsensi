package romara.rr.pdsidigitalabsensi.view

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_layout.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.ext.spSetToday
import romara.rr.pdsidigitalabsensi.ext.spSetToken
import romara.rr.pdsidigitalabsensi.ext.spSetUserdata
import romara.rr.pdsidigitalabsensi.interfaces.login.iLogin
import romara.rr.pdsidigitalabsensi.local.SharedPrefManager
import romara.rr.pdsidigitalabsensi.model.User.MLogin
import romara.rr.pdsidigitalabsensi.presenter.LoginPresenter

class LoginActivity : AppCompatActivity(), iLogin {

    private val presenter by lazy {
        LoginPresenter(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        // Init
        presenter

        // Event
        login_button.setOnClickListener {
            presenter.onLogin(this, input_username.text.toString(), input_password.text.toString())
//            dummyLogin()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dummyLogin(){
        spSetToday()

        startActivity(intentFor<HomeActivity>().newTask())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDataCompleteFromApi(q: MLogin) {
        spSetToken(q.token)
        spSetUserdata(q.data.username)
        spSetToday()

        startActivity(intentFor<HomeActivity>().newTask())
    }

    override fun onDataErrorFromApi(throwable: Throwable) {

    }

    override fun onStart() {
        super.onStart()
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(intentFor<HomeActivity>().newTask())
        }
    }

}