package romara.rr.pdsidigitalabsensi.base.approval

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.approve_layout.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.BaseListAdapter
import romara.rr.pdsidigitalabsensi.model.approval.MApproveUser

class RVApproveAdapter(private val listener: (position: Int, action: String) -> Unit) :
        BaseListAdapter<RecyclerView.ViewHolder, MApproveUser>() {

    private var data = mutableListOf<MApproveUser>()

    override fun setData(data: MutableList<MApproveUser>) {
        this.data = data
        this.notifyDataSetChanged()
    }

    override fun getData(position: Int): MApproveUser {
        return data[position]
    }

    override fun addData(data: MutableList<MApproveUser>) {
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun getAll(): MutableList<MApproveUser> {
        return this.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.approve_layout, parent, false), listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is Holder -> holder.bindAttend(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

}

class Holder(v: View, listener: (position: Int, action: String) -> Unit) : RecyclerView.ViewHolder(v) {

    init {
        itemView.approve_button.onClick { listener(adapterPosition, "A") }
        itemView.reject_button.onClick { listener(adapterPosition, "R") }
    }

    fun bindAttend(data: MApproveUser) {

        if (data.type == "return") {
            itemView.approve_card.setBackgroundResource(R.drawable.bg_approve_return)
            itemView.type_text.text = "Pulang"
        }
        itemView.approve_name.text = data.fullname
        itemView.approve_nip.text = "NIP." + data.nip
        itemView.time_view.text = data.time.split(".")[0]
        itemView.remark_text.text = data.remark

    }

}
