package romara.rr.pdsidigitalabsensi.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerview_layout.*
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.interfaces.absensi.iAbsensi
import romara.rr.pdsidigitalabsensi.model.Attend
import romara.rr.pdsidigitalabsensi.presenter.AbsensiPresenter

class AbsensiActivity : AppCompatActivity(), iAbsensi {

    private val presenter by lazy { AbsensiPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        presenter

        back_button.setOnClickListener { onBackPressed() }
//        setRecyclerData()
    }

    private fun setRecyclerData() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun onDataCompleteFromApi(q: Attend) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDataErrorFromApi(throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}