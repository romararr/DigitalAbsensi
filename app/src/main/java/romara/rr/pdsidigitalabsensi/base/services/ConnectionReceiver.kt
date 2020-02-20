package romara.rr.pdsidigitalabsensi.base.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast

@SuppressLint("HardwareIds")
@Suppress("DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ConnectionReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        if (connectReceiverListener != null) {
            connectReceiverListener!!.onNetworkConnectionChanged(isConnectedOrConnecting(context))
        }
    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        if (networkInfo != null) {
            if (networkInfo.type == 1) checkMacAddress(context)
        }

        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    private fun checkMacAddress(context: Context) {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wInfo = wifiManager.connectionInfo
        val macAddress = wInfo.macAddress
        val ssid = wInfo.ssid

        Toast.makeText(context, "$macAddress $ssid", Toast.LENGTH_SHORT)
        Log.d("MAC", "$macAddress $ssid")
    }

    interface ConnectReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectReceiverListener: ConnectReceiverListener? = null
    }

}