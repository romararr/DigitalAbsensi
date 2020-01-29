package romara.rr.pdsidigitalabsensi.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.absen.RVAbsenAdapter
import romara.rr.pdsidigitalabsensi.ext.gone
import romara.rr.pdsidigitalabsensi.ext.initTime
import romara.rr.pdsidigitalabsensi.ext.visible
import romara.rr.pdsidigitalabsensi.interfaces.absensi.iAbsensi
import romara.rr.pdsidigitalabsensi.model.absen.MAttend
import romara.rr.pdsidigitalabsensi.presenter.AbsensiPresenter
import java.util.*

class AbsensiActivity : AppCompatActivity(), iAbsensi {

    private val presenter by lazy { AbsensiPresenter(this) }
    lateinit var rvAdapter: RVAbsenAdapter

    val date = LocalDate.now(ZoneId.of("UTC"))
    val currentYear = date.year
    val currentMonth = date.monthValue
    val currentDay = date.dayOfMonth

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        // init
        presenter
        initTime()
        rvAdapter = RVAbsenAdapter { position -> null }
        presenter.onGetData(this)
        header_title.text = "Recent Absensi"

        back_button.setOnClickListener { onBackPressed() }
        searchbar.isEnabled = false

        initActions()
    }

    private fun setRecyclerData(m: MAttend) {
        recyclerview.visible()
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            rvAdapter.setData(m.data)
        }
    }

    override fun onDataCompleteFromApi(q: MAttend) {
        if (q.status == true) {
            loading_view.gone()
            recyclerview.visible()
            setRecyclerData(q)
        } else {
            toast(q.message)
        }
    }

    override fun onDataErrorFromApi(throwable: Throwable) {
//        showErrorDialog()
        toast("Network Error")
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

    fun initActions() {
        header_title.onClick {
            toast("hello")
            val datepick = DatePickerDialog(
                applicationContext,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    val dateString = selectedDate.format(dateFormatter)
                    searchbar.setText(dateString)
                },
                currentYear,
                currentMonth - 1,
                currentDay
            )
            datepick.show()
        }
    }
}