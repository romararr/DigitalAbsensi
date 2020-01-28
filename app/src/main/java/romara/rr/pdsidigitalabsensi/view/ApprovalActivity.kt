package romara.rr.pdsidigitalabsensi.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.bottomPadding
import org.jetbrains.anko.toast
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.approval.RVApproveAdapter
import romara.rr.pdsidigitalabsensi.constants.ConstVar
import romara.rr.pdsidigitalabsensi.constants.Constant
import romara.rr.pdsidigitalabsensi.ext.gone
import romara.rr.pdsidigitalabsensi.ext.onLogout
import romara.rr.pdsidigitalabsensi.ext.visible
import romara.rr.pdsidigitalabsensi.interfaces.approval.iApproval
import romara.rr.pdsidigitalabsensi.model.approval.MApprove
import romara.rr.pdsidigitalabsensi.presenter.ApprovePresenter

class ApprovalActivity : AppCompatActivity(), iApproval {

    private val presenter by lazy { ApprovePresenter(this) }
    lateinit var rvAdapter: RVApproveAdapter
    lateinit var mData: MApprove

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        // Init
        presenter
//        presenter.getApprovalData(this, 1)
        rvAdapter = RVApproveAdapter { position, action ->
            presenter.approveUser(this, mData.data[position].id, action)
        }
        header_title.text = "Approve Absen"
        dummyRV()

        back_button.setOnClickListener { onBackPressed() }
    }

    override fun onDataCompleteFromApi(q: MApprove) {
        mData = q

        if (mData.message == ConstVar.EXPIRED) {
            toast("Silahkan Login kembali")
            return onLogout(this)
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

    private fun dummyRV() {
        loading_view.gone()
        recyclerview.visible()
        recyclerview.clipToPadding = false
        recyclerview.bottomPadding = 40
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            rvAdapter.setData(Constant.dataApproval()) // Dummy Data Log Absen
        }
    }

}