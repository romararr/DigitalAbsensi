package romara.rr.pdsidigitalabsensi.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerview_layout.*
import org.jetbrains.anko.bottomPadding
import org.jetbrains.anko.toast
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.approval.RVApproveAdapter
import romara.rr.pdsidigitalabsensi.constants.ConstVar
import romara.rr.pdsidigitalabsensi.ext.gone
import romara.rr.pdsidigitalabsensi.ext.onExpired
import romara.rr.pdsidigitalabsensi.ext.visible
import romara.rr.pdsidigitalabsensi.interfaces.approval.iApproval
import romara.rr.pdsidigitalabsensi.model.approval.MApprove
import romara.rr.pdsidigitalabsensi.model.approval.MApproveUser
import romara.rr.pdsidigitalabsensi.presenter.ApprovePresenter
import java.util.*


class ApprovalActivity : AppCompatActivity(), iApproval {

    private val presenter by lazy { ApprovePresenter(this) }
    private lateinit var rvAdapter: RVApproveAdapter
    private lateinit var originalData: MutableList<MApproveUser>
    private lateinit var filteredData: MutableList<MApproveUser>

    private var tempPosition = 0
    private var tempId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        // Init
        presenter
        presenter.getApprovalData(this)
        initView()
//        dummyRV()
        initActions()

    }

    private fun initView() {
        rvAdapter = RVApproveAdapter(
            this,
            { position, action ->
                tempPosition = position
                tempId = filteredData[position].id
                recyclerview.gone()
                loading.visible()
                loading_view.visible()
                loading_empty.gone()
                presenter.approveUser(this, filteredData[position].id, action)
            })

        header_title.text = "Approve Absen"
        searchbar.isFocusable = false
        searchbar.isFocusableInTouchMode = true
    }

    private fun initActions() {
        back_button.setOnClickListener { onBackPressed() }
        searchbar.addTextChangedListener(object : TextWatcher {
            @SuppressLint("DefaultLocale")
            override fun afterTextChanged(p0: Editable?) {
                searchData(originalData)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })
    }

    @SuppressLint("DefaultLocale")
    private fun searchData(original: MutableList<MApproveUser>) {
        val filter = searchbar.text.toString().toLowerCase().trim()
        filteredData = original.filter { data ->
            data.fullname.toLowerCase().contains(filter)
        }.toMutableList()

        val byNip = original.filter { data ->
            data.nip.toLowerCase().contains(filter)
        }.toMutableList()
        val byTime = original.filter { data ->
            data.time.toLowerCase().contains(filter)
        }.toMutableList()
        val byRemark = original.filter { data ->
            data.remark.toLowerCase().contains(filter)
        }.toMutableList()

        if (filter.isNotEmpty()) {
            filteredData.addAll(byNip + byRemark + byTime)
        }

        rvAdapter.setData(filteredData)
    } // Search Approve Data

    private fun setRecyclerData(m: MutableList<MApproveUser>) {
        loading_view.gone()
        recyclerview.visible()
        recyclerview.clipToPadding = false
        recyclerview.bottomPadding = 50
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            rvAdapter.setData(m)
        }
    } // Setup Recyclerview

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onDataCompleteFromApi(q: MApprove) {

        loading.gone()

        if (q.message == ConstVar.EXPIRED) return onExpired(this)

        if (q.status) {
            loading_view.gone()
            recyclerview.visible()

            if (q.data.isNullOrEmpty()) {
                toast(q.message)
                originalData =
                    originalData.filterIndexed { index, obj -> obj.id != tempId }.toMutableList()
//                rvAdapter.removeItem(tempPosition)
                searchData(originalData)
            } else {
                originalData = q.data
                filteredData = originalData
                setRecyclerData(filteredData)
            }

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

//    fun showErrorDialog() {
//        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
//        val dialogView: View =
//            LayoutInflater.from(this).inflate(R.layout.net_error_layout, viewGroup, false)
//        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//        builder.setView(dialogView)
//
//        val alertDialog: AlertDialog = builder.create()
//        alertDialog.show()
//    }

//    private fun dummyRV() {
//        loading_view.gone()
//        recyclerview.visible()
//        recyclerview.clipToPadding = false
//        recyclerview.bottomPadding = 40
//        recyclerview.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = rvAdapter
//            rvAdapter.setData(Constant.dataApproval()) // Dummy Data Log Absen
//        }
//    }

}