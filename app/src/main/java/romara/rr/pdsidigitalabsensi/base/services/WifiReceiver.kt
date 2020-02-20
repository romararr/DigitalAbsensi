package romara.rr.pdsidigitalabsensi.base.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import android.util.Log


class WifiReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (wifiReceiverListener != null) {
            wifiReceiverListener!!.onWifiChange(isConnecting(context, intent))
        }
    }

    private fun isConnecting(context: Context, intent: Intent): Boolean {
        val action = intent.action
        if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION == action) {
            val state =
                intent.getParcelableExtra<SupplicantState>(WifiManager.EXTRA_NEW_STATE)
            if (SupplicantState.isValidState(state)
                && state == SupplicantState.COMPLETED
            ) {
                val connected = checkConnectedToDesiredWifi(context!!)

                Log.d("ConnectionStatus", connected.toString())
                return connected
            }
        }

        return false
    }

    /** Detect you are connected to a specific network.  */
    private fun checkConnectedToDesiredWifi(context: Context): Boolean {
        var connected = false
        val desiredMacAddress = "F0-03-8C-43-72-93"
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifi = wifiManager.connectionInfo
        if (wifi != null) { // get current router Mac address
            val bssid = wifi.bssid
            connected = desiredMacAddress == bssid
        }
        return connected
    }

    interface ConnectReceiveListener {
        fun onWifiChange(isConnected: Boolean)
    }

    companion object {
        var wifiReceiverListener: ConnectReceiveListener? = null
    }
}