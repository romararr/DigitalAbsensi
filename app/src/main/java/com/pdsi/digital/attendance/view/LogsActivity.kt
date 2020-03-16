package com.pdsi.digital.attendance.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
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
import com.pdsi.digital.attendance.constants.Constant
import com.pdsi.digital.attendance.R
import com.pdsi.digital.attendance.base.logs.RVLogsAdapter
import com.pdsi.digital.attendance.constants.ConstVar
import com.pdsi.digital.attendance.ext.*
import com.pdsi.digital.attendance.interfaces.login.iLogs
import com.pdsi.digital.attendance.model.location.MLocation
import com.pdsi.digital.attendance.presenter.LogPresenter

class LogsActivity : AppCompatActivity(), iLogs {

    private val presenter by lazy { LogPresenter(this) }

    lateinit var rvAdapter: RVLogsAdapter

    val date = LocalDate.now(ZoneId.of("UTC"))
    val currentYear = date.year
    val currentMonth = date.monthValue
    val currentDay = date.dayOfMonth

    var toDate = ""
    var fromDate = ""

    // Format Date
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val msFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        // Init
        presenter
        presenter.onGetData(this)
        initTime()
        initView()

        // Actions
        initActions()
    }


    private fun initView() {
        rvAdapter = RVLogsAdapter { position -> null }

        header_title.text = "Recent Location"
        searchbar.setHint("From...")
        searchbar.inputType = InputType.TYPE_NULL
        search_icon.gone()
        searchbar_limit.inputType = InputType.TYPE_NULL
        searchview_limit.visible()
        search_button.visible()
    }

    private fun setRecyclerData(m: MLocation) {
        recyclerview.visible()
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            rvAdapter.setData(m.data)
        }
    }

    private fun dummyRV() {
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            rvAdapter.setData(Constant.dataLogAbsen()) // Dummy Data Log Absen
        }
    }

    override fun onDataCompleteFromApi(q: MLocation) {
        loading.gone()

        if (q.message == ConstVar.EXPIRED) return onExpired(this)

        if (q.status == true) {
            loading_view.gone()
            recyclerview.visible()
            setRecyclerData(q)
            rvAdapter.setData(q.data)
        } else {
            recyclerview.gone()
            loading_view.visible()
            loading_empty.visible()
            toast(q.message)
        }
    }

    override fun onDataErrorFromApi(throwable: Throwable) {
        recyclerview.gone()
        loading.gone()
        loading_view.visible()
        loading_empty.visible()
        toast("Network Error")
    }

    fun initActions() {
        back_button.onClick { onBackPressed() }
        searchbar.onClick { pickerFromDate() }
        searchview.onClick { pickerFromDate() }
        searchbar_limit.onClick { pickerToDate() }
        searchview_limit.onClick { pickerToDate() }
        search_button.onClick {
            var to = toDate.split(" ")[0].replace("/", "-")
            var from = fromDate.split(" ")[0].replace("/", "-")
            if (to.isEmpty() || from.isEmpty()) {
                toast("Tanggal harus lengkap")
            } else {
                loading_view.visible()
                loading.visible()
                loading_empty.gone()
                presenter.onGetData(this@LogsActivity, to, from)
            }
        }
    }

    fun pickerFromDate() {
        val pickFrom = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                val dateString = selectedDate.format(dateFormatter)
                fromDate = selectedDate.format(msFormatter) + " 00:00:00"
                searchbar.setText(dateString)
            },
            currentYear,
            currentMonth - 1,
            currentDay
        )
        pickFrom.show()
    }

    fun pickerToDate() {
        val pickTo = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                val dateString = selectedDate.format(dateFormatter)
                toDate = selectedDate.format(msFormatter) + " 00:00:00"
                searchbar_limit.setText(dateString)
            },
            currentYear,
            currentMonth - 1,
            currentDay
        )
        if (searchbar.text.isNotEmpty()) pickTo.datePicker.minDate = getMillis(fromDate)
        pickTo.show()
    }
}