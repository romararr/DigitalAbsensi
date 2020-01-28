package romara.rr.pdsidigitalabsensi.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.toast
import romara.rr.pdsidigitalabsensi.constants.Constant
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.logs.RVLogsAdapter
import romara.rr.pdsidigitalabsensi.ext.gone
import romara.rr.pdsidigitalabsensi.ext.initTime
import romara.rr.pdsidigitalabsensi.ext.visible
import romara.rr.pdsidigitalabsensi.interfaces.login.iLogs
import romara.rr.pdsidigitalabsensi.model.location.MLocation
import romara.rr.pdsidigitalabsensi.presenter.LogPresenter

class LogsActivity : AppCompatActivity(), iLogs {

    private val presenter by lazy { LogPresenter(this) }

    lateinit var rvAdapter: RVLogsAdapter

    var searchDate: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        // Init
        presenter
        initTime()
        rvAdapter = RVLogsAdapter { position -> null }
        presenter.onGetData(this)
        header_title.text = "Recent Location"

        // Init Value
//        date_text.text = searchDate

        // Actions
//        selectdate_button.setOnClickListener { datePick() }
        back_button.setOnClickListener { onBackPressed() }
    }

    private fun dummyRV() {
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            rvAdapter.setData(Constant.dataLogAbsen()) // Dummy Data Log Absen
        }
    }

    override fun onDataCompleteFromApi(q: MLocation) {
        if (q.status == true) {
            loading_view.gone()
            recyclerview.visible()
            recyclerview.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = rvAdapter
                rvAdapter.setData(q.data) // Dummy Data Log Absen
            }
        } else {
            toast(q.message)
        }
    }

    override fun onDataErrorFromApi(throwable: Throwable) {
        showErrorDialog()
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