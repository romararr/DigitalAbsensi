package romara.rr.pdsidigitalabsensi.view

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.toast
import romara.rr.pdsidigitalabsensi.Constant
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.logs.RVLogsAdapter
import romara.rr.pdsidigitalabsensi.interfaces.login.iLogs
import romara.rr.pdsidigitalabsensi.model.Location.MLocation
import romara.rr.pdsidigitalabsensi.presenter.LogPresenter
import java.util.*

class LogsActivity : AppCompatActivity(), iLogs {

    private val presenter by lazy { LogPresenter(this) }

    lateinit var rvAdapter: RVLogsAdapter

    var searchDate: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        // Init
        presenter
        rvAdapter = RVLogsAdapter { position -> null }
        presenter.onGetData(this)
//        dummyRV()

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}