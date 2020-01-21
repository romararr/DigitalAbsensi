package romara.rr.pdsidigitalabsensi.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.new_home_layout.*
import kotlinx.android.synthetic.main.out_office_note.*
import kotlinx.android.synthetic.main.out_office_note.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.constants.ConstVar
import romara.rr.pdsidigitalabsensi.ext.*
import romara.rr.pdsidigitalabsensi.interfaces.home.iHome
import romara.rr.pdsidigitalabsensi.model.Location.MLocation
import romara.rr.pdsidigitalabsensi.presenter.HomePresenter
import java.text.SimpleDateFormat


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
        setContentView(R.layout.new_home_layout)

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

//        absen_button.onClick { onAbsenClick() }
//        break_button.onClick { onBreakClick() }
//        r_absen_button.onClick { startActivity(intentFor<AbsensiActivity>()) }
//        r_map_button.onClick { startActivity(intentFor<LogsActivity>()) }
//        logout_button.onClick { onLogout() }

        masuk_button.onClick { absenCome() }
        pulang_button.onClick { absenReturn() }
        istirahat_button.onClick { absenBreak() }
        end_istirahat_button.onClick { absenEndBreak() }
        historyabsen_button.onClick { startActivity<AbsensiActivity>() }
        historymap_button.onClick { startActivity<LogsActivity>() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onLogout() {
        spClearTime()
        spClear()

        startActivity<LoginActivity>()
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
                            time_view.setText(dateStr)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDataCompleteFromApi(q: MLocation, type: String, resCondition: String) {

        absenPressed = false
        breakPressed = false
        item_menu.visible()
        loading_view.gone()

        if (q.status == false) {
            when (q.condition) {
                ConstVar.SUDAHABSEN -> toast("Anda Sudah Absen")
                ConstVar.ATTENDYET -> toast("Anda Belum Absen Masuk")
                else -> toast(q.message)
            }
            return
        }

        // Absen Masuk
//        when (type) {
//            ConstVar.COME -> absenCome()
//            ConstVar.RETURN -> absenReturn()
//            ConstVar.BREAK -> absenBreak()
//            ConstVar.ENDBREAK -> absenEndBreak()
//        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun absenCome() {
        item_menu.gone()
        loading_view.visible()
        presenter.onAttend(applicationContext, ConstVar.COME, "datang")
//        absen_button_text.setText("Anda Sudah Absen")
//        absen_button.setBackgroundResource(R.drawable.rounded_button_disabled)
//
//        break_button.setBackgroundResource(R.drawable.rounded_button)
//        break_button_text.setText("BREAK")
//
//        spSetTimeCome()
//        time_in.setText(DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun absenReturn() {
        item_menu.gone()
        loading_view.visible()
        presenter.onAttend(applicationContext, ConstVar.RETURN, "pulang")
//        absen_button_text.setText("Terima Kasih")
//        absen_button.setBackgroundResource(R.drawable.rounded_button_disabled)
//
//        spSetTimeOut()
//        time_out.setText(
//            DateTimeFormatter.ofPattern("HH:mm").format(LocalDateTime.now())
//        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun absenBreak() {
        item_menu.gone()
        loading_view.visible()
        presenter.onAttend(applicationContext, ConstVar.BREAK, "istirahat")
//        spSetBreak()
//        break_button.setBackgroundResource(R.drawable.rounded_button_disabled)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun absenEndBreak() {
        item_menu.gone()
        loading_view.visible()
        presenter.onAttend(applicationContext, ConstVar.ENDBREAK, "selesai istirahat")
//        spSetEndBreak()
//        break_button.setBackgroundResource(R.drawable.rounded_button_disabled)
    }

    override fun onDataErrorFromApi(throwable: Throwable) {
        absenPressed = false
        breakPressed = false
        item_menu.visible()
        loading_view.gone()
        showErrorDialog()
        Log.d("ERR API", throwable.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFirstView(today: Boolean?, time: String) {
        if (today == true) {
            if (come.isNotEmpty()) time_masuk.text = presenter.parseTime(come)
            if (out.isNotEmpty()) time_pulang.text = presenter.parseTime(out)
            if (breakTime.isNotEmpty()) time_break.text = presenter.parseTime(breakTime)
            if (endbreakTime.isNotEmpty()) time_endbreak.text = presenter.parseTime(endbreakTime)
        } else {
            spClearTime()
            getFirstView(true, "")
        }
//        when (today) {
//            true -> {
//                Log.d("TODAY", "yess")
//
//                // Set If Today
//                if (out.isEmpty()) {
//
//                    Log.d("BEFORE MASUK", "okee")
//                    // Before Come
//                    if (time.isEmpty()) {
//
//                        absen_button_text.setText("M A S U K")
//                        absen_button.setBackgroundResource(R.drawable.rounded_button)
//
//                        break_button.setBackgroundResource(R.drawable.rounded_button_disabled)
//                        break_button_text.setText("BREAK")
//
//                    }
//                    // After Come
//                    else {
//
//                        time_in.setText(presenter.parseTime(come))
//
//                        Log.d("AFTER MASUK", "okee")
//                        if (presenter.getDifferentTime(time) > diffDefault) {
//
//                            absen_button_text.setText("P U L A N G")
//                            absen_button.setBackgroundResource(R.drawable.rounded_button_return)
//
//                        } else {
//
//                            absen_button_text.setText("Anda Sudah Absen Pagi")
//                            absen_button.setBackgroundResource(R.drawable.rounded_button_disabled)
//
//                        }
//
//                        getBreakStatus()
//                    }
//                } else {
//
//                    getBreakStatus()
//
//                    if (time.isNotEmpty()) {
//                        absen_button_text.setText("Anda Sudah Absen Pulang")
//                        absen_button.setBackgroundResource(R.drawable.rounded_button_disabled)
//                    }
//
//                    time_out.setText(presenter.parseTime(out))
//
//                }
//            }
//            false -> {
//
//                Log.d("TODAY", "noo")
//                spClearTime()
//                getFirstView(true, "")
//            }
//
//        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAbsenClick() {

        // Absen Lengkap
        if ((come.isNotEmpty() && out.isNotEmpty()) || absenPressed == true) {
            toast("Anda Sudah Absen")
            return
        }

        // Set Loading Absen
//        absen_button_text.text = "Loading..."

        if (come.isEmpty()) {

            // Absen Masuk
            presenter.onAttend(this, ConstVar.COME, "datang")
            absenPressed = true

        } else {

            // Absen Pulang
            if (presenter.getDifferentTime(come) > diffDefault) {

                presenter.onAttend(this, ConstVar.RETURN, "pulang")
                breakPressed = true

            } else {
                toast("Anda Sudah Absen")
            }

        }

    }

    // Break Absen
    @RequiresApi(Build.VERSION_CODES.O)
    fun getBreakStatus() {

        if (breakTime.isNotEmpty() && endbreakTime.isNotEmpty()) {
//            break_button_text.setText("BREAK")
//            break_button.setBackgroundResource(R.drawable.rounded_button_disabled)
            return
        }

        if (breakTime.isNotEmpty()) {

            if (presenter.getDifferentTime(breakTime) > 15000) {
//                break_button_text.setText("END BREAK")
//                break_button.setBackgroundResource(R.drawable.rounded_button_return)
            } else {

                if (endbreakTime.isNotEmpty()) {
//                    break_button_text.setText("END BREAK")
//                    break_button.setBackgroundResource(R.drawable.rounded_button_disabled)
                }

            }

        } else {
//            break_button_text.setText("BREAK")
//            break_button.setBackgroundResource(R.drawable.rounded_button)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onBreakClick() {

        if (come.isEmpty()) return
        if (breakPressed == true) {
            toast("Anda Sudah Istirahat")
            return
        }

//        break_button_text.text = "Loading..."

        if (breakTime.isEmpty()) {

            // BREAK START
            presenter.onAttend(this, ConstVar.BREAK, "ISTIRAHAT START")
            breakPressed = true

        } else

        // BREAK END
            if (presenter.getDifferentTime(breakTime) > 15000) {

                if (endbreakTime.isEmpty()) {

                    presenter.onAttend(this, ConstVar.ENDBREAK, "ISTIRAHAT END")
                    breakPressed = true
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

                        spSetLocation(location.latitude, location.longitude)
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

            spSetLocation(lastLocation.latitude, lastLocation.longitude)
        }
    }

    fun showNoteDialog(condition: String) {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.out_office_note, viewGroup, false)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        val alertDialog: AlertDialog = builder.create()

        dialogView.manualcome_button.setOnClickListener {
            if (dialogView.note_text.equals("")) {
                toast("Note harus diisi")
            } else {
                presenter.onAttendOutOffice(
                    this,
                    condition,
                    note_text.text.toString()
                )
            }
        }

        alertDialog.show()
    }

    fun showErrorDialog() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.net_error_layout, viewGroup, false)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}