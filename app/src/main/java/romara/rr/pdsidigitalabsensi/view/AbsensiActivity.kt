package romara.rr.pdsidigitalabsensi.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
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
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.absen.RVAbsenAdapter
import romara.rr.pdsidigitalabsensi.constants.ConstVar
import romara.rr.pdsidigitalabsensi.ext.*
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

    var toDate = ""
    var fromDate = ""

    // Format Date
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val msFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        // init
        presenter
        initTime()
        presenter.onGetData(this)
        initView()

        initActions()
    }

    private fun initView() {
        rvAdapter = RVAbsenAdapter { position -> null }

        header_title.text = "Recent Absensi"
        searchbar.setHint("From...")
        searchbar.inputType = InputType.TYPE_NULL
//        search_icon.setImageResource(R.drawable.cal)
        search_icon.gone()
        searchbar_limit.inputType = InputType.TYPE_NULL
        searchview_limit.visible()
        search_button.visible()
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
//        showErrorDialog()
        recyclerview.gone()
        loading.gone()
        loading_view.visible()
        loading_empty.visible()
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
        back_button.setOnClickListener { onBackPressed() }
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
                presenter.onGetData(this@AbsensiActivity, to, from)
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