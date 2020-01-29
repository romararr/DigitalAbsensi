package romara.rr.pdsidigitalabsensi.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.new_home_layout.*
import kotlinx.android.synthetic.main.out_office_note.*
import kotlinx.android.synthetic.main.out_office_note.view.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.BaseActivity
import romara.rr.pdsidigitalabsensi.constants.ConstVar
import romara.rr.pdsidigitalabsensi.ext.*
import romara.rr.pdsidigitalabsensi.interfaces.home.iHome
import romara.rr.pdsidigitalabsensi.model.location.MLocation
import romara.rr.pdsidigitalabsensi.model.user.MUser
import romara.rr.pdsidigitalabsensi.presenter.HomePresenter
import java.text.SimpleDateFormat


class HomeActivity : BaseActivity(), iHome {

    private val presenter by lazy { HomePresenter(this) }
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 42

    var come = spGetTimeCome()
    var out = spGetTimeOut()
    var breakTime = spGetBreak()
    var endbreakTime = spGetEndBreak()
    var today = spGetToday()
    var recentLocation: String? = ""

    companion object {
        var instance: HomeActivity? = null
            get() = instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_home_layout)

        instance = this

        // Init
        presenter
        initTime()
        profile_name.text = spGetUser()
        profile_nip.text = "NIP." + spGetNip()

        Log.d("TOKEN", spGetToken())

        // Get Location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        liveLocation()
        liveTime()

        presenter.getTimeOnLogin(this)

        // Actions
        masuk_button.onClick { absenCome() }
        pulang_button.onClick { absenReturn() }
        istirahat_button.onClick { absenBreak() }
        end_istirahat_button.onClick { absenEndBreak() }
        verify_button.onClick { startActivity<ApprovalActivity>() }
        menu_button.onClick { v -> showPopup(v!!) }
        location_view.onClick { openMap(spGetLocation("latitude").toDouble(), spGetLocation("longitude").toDouble()) }
    }

    // Live Data
    private fun liveTime() {
        val t: Thread = object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        sleep(1000)
                        runOnUiThread {
                            val date = System.currentTimeMillis()
                            val sdf = SimpleDateFormat("HH:mm")
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

    override fun onDataCompleteFromApi(q: MLocation, type: String, resCondition: String) {

        endLoading()

        if (q.message == ConstVar.EXPIRED) {
            toast("Silahkan Login kembali")
            return onLogout(this)
        } // Token Expired

        if (q.status == false) {
            when (q.condition) {
                ConstVar.SUDAHABSEN -> toast("Anda Sudah Absen")
                ConstVar.ATTENDYET -> toast("Anda Belum Absen Masuk")
                else -> toast(q.message)
            }
            return
        }

        // Absen Response
        when (type) {
            ConstVar.COME -> {
                if (q.condition == ConstVar.OUTOFFICE) {
                    showNoteDialog(ConstVar.COME)
                    return
                }

                if (ConstVar.FAR_FROMOFFICE in q.message) {
                    longToast(ConstVar.MSG_FAROFFICE)
                    endLoading()
                }
                else absenCome(true)

            } // Absen Datang
            ConstVar.RETURN -> {
                if (come.isEmpty()) {
                    toast("Anda belum absen datang")
                    return
                }

                if (q.condition == ConstVar.OUTOFFICE || q.condition == ConstVar.MANUAL) {
                    showNoteDialog(ConstVar.RETURN)
                    return
                }

                if (ConstVar.FAR_FROMOFFICE in q.message) {
                    longToast(ConstVar.MSG_FAROFFICE)
                    endLoading()
                }
                else absenReturn(true)

            } // Absen Pulang
            ConstVar.BREAK -> {
                if (q.condition == ConstVar.OUTOFFICE) {
                    showNoteDialog(ConstVar.BREAK)
                    return
                }

                if (ConstVar.FAR_FROMOFFICE in q.message) {
                    longToast(ConstVar.MSG_FAROFFICE)
                    endLoading()
                }
                else absenBreak(true)

            } // Absen Istirahat
            ConstVar.ENDBREAK -> {

                if (q.condition == ConstVar.OUTOFFICE) {
                    showNoteDialog(ConstVar.ENDBREAK)
                    return
                }

                if (ConstVar.FAR_FROMOFFICE in q.message) {
                    longToast(ConstVar.MSG_FAROFFICE)
                    endLoading()
                }
                else absenEndBreak(true)

            } // Absen Selesai Istirahat
            else -> endLoading()
        }
    }

    override fun onGetAbsenCompleteApi(q: MUser) {
        val data = q.data[0]
//        date + "T" + data.time_come.split(".")[0]

        if (data.time_come.isNullOrBlank() == false) {
            spSetTimeCome(data.time_come)
            come = spGetTimeCome()
        }
        if (data.time_return.isNullOrBlank() == false) {
            spSetTimeOut(data.time_return)
            out = spGetTimeOut()
        }
        if (data.time_break.isNullOrBlank() == false) {
            spSetBreak(data.time_break)
            breakTime = spGetBreak()
        }
        if (data.time_endbreak.isNullOrBlank() == false) {
            spSetEndBreak(data.time_endbreak)
            endbreakTime = spGetEndBreak()
        }

        getFirstView(today)
    }

    private fun endLoading() {
        item_menu.visible()
        loading_view.gone()
    } // End Loading

    private fun startLoading() {
        item_menu.gone()
        loading_view.visible()
    } // Start Loading

    private fun absenCome(res: Boolean? = false) {
        if (res == true) {
            spSetTimeCome()
            time_masuk.setText(DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now()))
            return
        }

        startLoading()
        presenter.onAttend(applicationContext, ConstVar.COME, "datang")
    }

    private fun absenReturn(res: Boolean? = false) {

        if (res == true) {
            spSetTimeOut()
            time_pulang.setText(DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now()))
            return
        }

        startLoading()
        presenter.onAttend(applicationContext, ConstVar.RETURN, "pulang")
    }

    private fun absenBreak(res: Boolean? = false) {

        if (res == true) {
            spSetBreak()
            time_break.setText(DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now()))
            return
        }

        startLoading()
        presenter.onAttend(applicationContext, ConstVar.BREAK, "istirahat")
    }

    private fun absenEndBreak(res: Boolean? = false) {

        if (res == true) {
            spSetEndBreak()
            time_endbreak.setText(DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now()))
            return
        }

        startLoading()
        presenter.onAttend(applicationContext, ConstVar.ENDBREAK, "selesai istirahat")

//        break_button.setBackgroundResource(R.drawable.rounded_button_disabled)
    }

    override fun onDataErrorFromApi(throwable: Throwable) {
        endLoading()
//        showErrorDialog()
        toast("Network Error")
        Log.d("ERR API", throwable.toString())
    }

    fun getFirstView(today: Boolean = true) {

//        if (spGetRole().equals(ConstVar.ADM)) verify_layout.visible()
        if (today == false) {
            Log.d("today", "kosong :(")
            spClearTime()
            getFirstView()
            return
        }

        Log.d("today", "ada dong")
        if (come.isNotEmpty()) time_masuk.text = presenter.parseTime(come)
        if (out.isNotEmpty()) time_pulang.text = presenter.parseTime(out)
        if (breakTime.isNotEmpty()) time_break.text = presenter.parseTime(breakTime)
        if (endbreakTime.isNotEmpty()) time_endbreak.text = presenter.parseTime(endbreakTime)
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
                startLoading()
                presenter.onAttendOutOffice(
                        this,
                        condition,
                        dialogView.note_text.text.toString()
                )
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    } // Note Out Office Attend

    fun showErrorDialog() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
                LayoutInflater.from(this).inflate(R.layout.net_error_layout, viewGroup, false)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    fun showPopup(view: View) {
        var popup: PopupMenu? = null
        popup = PopupMenu(this, view)
        popup.inflate(R.menu.profile_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.logout -> {
                    onLogout(this)
                }
                R.id.history_log -> {
                    startActivity<LogsActivity>()
                }
                R.id.history_absen -> {
                    startActivity<AbsensiActivity>()
                }
            }

            true
        })

        popup.show()

    } // User Menu Popup
}