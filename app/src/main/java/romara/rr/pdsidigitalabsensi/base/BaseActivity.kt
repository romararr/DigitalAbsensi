package romara.rr.pdsidigitalabsensi.base

import android.app.AlertDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.services.ConnectionReceiver

open class BaseActivity : AppCompatActivity(), ConnectionReceiver.ConnectReceiverListener {
    private var mSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(ConnectionReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun showError(isConnected: Boolean) {
        if (!isConnected) {

            val messageToUser = "You are offline."

            mSnackBar = Snackbar.make(findViewById(R.id.rootLayout), messageToUser, Snackbar.LENGTH_LONG)
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()
        } else {
            mSnackBar?.dismiss()
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showError(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectionReceiver.connectReceiverListener = this
    }

}