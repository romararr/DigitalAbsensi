package romara.rr.pdsidigitalabsensi.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.home_layout.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.ext.*
import romara.rr.pdsidigitalabsensi.interfaces.home.iHome
import romara.rr.pdsidigitalabsensi.model.Attend
import romara.rr.pdsidigitalabsensi.presenter.HomePresenter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeActivity : AppCompatActivity(), iHome {

    private val presenter by lazy { HomePresenter(this) }
    private val diffDefault = 3600000
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 42

    // User State
    var username: String? = ""

    // Absensi State
    var absenPressed: Boolean? = false
    var breakPressed: Boolean? = false

    var come = spGetTimeCome()
    var out = spGetTimeOut()
    var breakTime = spGetBreak()
    var endbreakTime = spGetEndBreak()
    @RequiresApi(Build.VERSION_CODES.O)
    var today = spGetToday()
    var recentLocation: String? = ""

    companion object {
        var instance: HomeActivity? = null
            get() = instance
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)

        instance = this

        // Init
        presenter
        username = spGetUser()
        profile_name.setText(username.toString())

        // Get Location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        liveLocation()

        getFirstView(today, if (out.isNotEmpty()) out else come)
        liveTime()

        absen_button.onClick { onAbsenClick() }
        break_button.onClick { onBreakClick() }
        r_absen_button.onClick { startActivity(intentFor<AbsensiActivity>()) }
        r_map_button.onClick { startActivity(intentFor<LogsActivity>()) }
        logout_button.onClick { onLogout() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onLogout() {
        spClearTime()
        spClear()

        startActivity(intentFor<LoginActivity>())
        finish()
    }

    // Live Data
    private fun liveTime() {
        val t: Thread = object : Thread() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                try {
                    while (!isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            val date = System.currentTimeMillis()
                            val sdf =
                                SimpleDateFormat("HH:mm")
                            val dateStr = sdf.format(date)
                            time_text.setText(dateStr)
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        t.start()
    }

    private fun liveLocation() {
        val locThread: Thread = object : Thread() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                try {
                    while (!isInterrupted) {
                        sleep(3000)
                        runOnUiThread {
                            getLastLocation()
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        locThread.start()
    }

    override fun onDataCompleteFromApi(q: Attend) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDataErrorFromApi(throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFirstView(today: Boolean?, time: String) {
        when (today) {
            true -> {
                Log.d("TODAY", "yess")

                // Set If Today
                if (out.isEmpty()) {

                    Log.d("BEFORE MASUK", "okee")
                    // Before Come
                    if (time.isEmpty()) {

                        absen_button_text.setText("M A S U K")
                        absen_button.setBackgroundResource(R.drawable.rounded_button)

                        break_button.setBackgroundResource(R.drawable.rounded_button_disabled)
                        break_button_text.setText("BREAK")

                    }
                    // After Come
                    else {

                        time_in.setText(
                            DateTimeFormatter.ofPattern("HH:mm").format(
                                LocalDateTime.parse(come)
                            )
                        )

                        Log.d("AFTER MASUK", "okee")
                        if (presenter.getDifferentTime(time) > diffDefault) {

                            absen_button_text.setText("P U L A N G")
                            absen_button.setBackgroundResource(R.drawable.rounded_button_return)

                        } else {

                            absen_button_text.setText("Anda Sudah Absen Pagi")
                            absen_button.setBackgroundResource(R.drawable.rounded_button_disabled)

                        }

                        getBreakStatus()
                    }
                } else {

                    getBreakStatus()

                    if (time.isNotEmpty()) {
                        absen_button_text.setText("Anda Sudah Absen Pulang")
                        absen_button.setBackgroundResource(R.drawable.rounded_button_disabled)
                    }

                    time_out.setText(
                        DateTimeFormatter.ofPattern("HH:mm").format(
                            LocalDateTime.parse(out)
                        )
                    )

                }
            }
            false -> {

                Log.d("TODAY", "noo")
                spClearTime()
                getFirstView(true, "")
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAbsenClick() {

        Log.d("COME", come)
        Log.d("COMOUT", out)

        if (come.isEmpty()) {

            Log.d("EMPTY", "oke")

            // Absen Masuk
            if (absenPressed == false) {
                spSetTimeCome()

                absen_button_text.setText("Anda Sudah Absen")
                absen_button.setBackgroundResource(R.drawable.rounded_button_disabled)

                break_button.setBackgroundResource(R.drawable.rounded_button)
                break_button_text.setText("BREAK")

                time_in.setText(DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now()))
                absenPressed = true
            } else {
                toast("Anda Sudah Absen")
            }

        } else {

            Log.d("NOTEMPTY", presenter.getDifferentTime(come).toString())

            // Absen Pulang
            if (presenter.getDifferentTime(come) > diffDefault && absenPressed == false) {
                absen_button_text.setText("Terima Kasih")
                absen_button.setBackgroundResource(R.drawable.rounded_button_disabled)

                spSetTimeOut()
                time_out.setText(
                    DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now())
                )

                breakPressed = true

            } else {
                toast("Anda Sudah Absen")
            }

        }

        // Absen Lengkap
        if (come.isNotEmpty() && out.isNotEmpty()) {

        }
    }

    // Break Absen
    @RequiresApi(Build.VERSION_CODES.O)
    fun getBreakStatus() {

        if (breakTime.isNotEmpty() && endbreakTime.isNotEmpty()) {
            break_button_text.setText("BREAK")
            break_button.setBackgroundResource(R.drawable.rounded_button_disabled)
            return
        }

        if (breakTime.isNotEmpty()) {

            if (presenter.getDifferentTime(breakTime) > 15000) {
                break_button_text.setText("END BREAK")
                break_button.setBackgroundResource(R.drawable.rounded_button_return)
            } else {

                if (endbreakTime.isNotEmpty()) {
                    break_button_text.setText("END BREAK")
                    break_button.setBackgroundResource(R.drawable.rounded_button_disabled)
                }

            }

        } else {
            break_button_text.setText("BREAK")
            break_button.setBackgroundResource(R.drawable.rounded_button)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onBreakClick() {

        if (come.isEmpty()) return

        if (breakTime.isEmpty()) {

            if (breakPressed == false) {
                spSetBreak()
                break_button.setBackgroundResource(R.drawable.rounded_button_disabled)

                breakPressed = true
            } else {
                toast("Anda Sudah Istirahat")
            }

        } else

            if (presenter.getDifferentTime(breakTime) > 15000) {

                if (endbreakTime.isEmpty() && breakPressed == false) {
                    spSetEndBreak()
                    break_button.setBackgroundResource(R.drawable.rounded_button_disabled)

                    breakPressed = true
                } else {
                    toast("Anda Sudah Istirahat")
                }
            } else {
                toast("Anda Sudah Istirahat")
            }

    }

    // Location
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toast("Granted Location")
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocation()
                    } else {
                        recentLocation =
                            getCompleteAdress(this, location.latitude, location.longitude)
                        location_text.setText(recentLocation)

                        Log.d("lokasi", getCompleteAdress(this, location.latitude, location.longitude))
                    }
                }
            } else {
                toast("Turn On GPS")
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestLocPermission(this)
        }
    }

    private fun requestNewLocation() {
        var locRequest = LocationRequest()
        locRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locRequest.interval = 20000
        locRequest.fastestInterval = 5000
        locRequest.numUpdates = 1

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(
            locRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation

            recentLocation =
                getCompleteAdress(this@HomeActivity, lastLocation.latitude, lastLocation.longitude)
            location_text.setText(recentLocation)
        }
    }

}