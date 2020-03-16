package com.pdsi.digital.attendance.base.approval

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.approve_layout.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import com.pdsi.digital.attendance.R
import com.pdsi.digital.attendance.base.BaseListAdapter
import com.pdsi.digital.attendance.constants.ConstVar
import com.pdsi.digital.attendance.ext.gone
import com.pdsi.digital.attendance.ext.spGetRole
import com.pdsi.digital.attendance.ext.visible
import com.pdsi.digital.attendance.model.approval.MApproveUser

class RVApproveAdapter(
    context: Context,
    private val listener: (position: Int, action: String) -> Unit
) :
    BaseListAdapter<RecyclerView.ViewHolder, MApproveUser>() {

    private var data = mutableListOf<MApproveUser>()
    private var ctx = context

    override fun setData(data: MutableList<MApproveUser>) {
        this.data = data
        this.notifyDataSetChanged()
    }

    override fun removeItem(position: Int) {
        this.data.removeAt(position)
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
            is Holder -> holder.bindAttend(ctx, data[position])
        }
    }

    override fun getItemCount(): Int = data.size

}

class Holder(v: View, listener: (position: Int, action: String) -> Unit) :
    RecyclerView.ViewHolder(v) {

    init {
        itemView.approve_button.onClick { listener(adapterPosition, "A") }
        itemView.reject_button.onClick { listener(adapterPosition, "R") }
    }

    fun bindAttend(context: Context, data: MApproveUser) {

        val time = data.time.split(".")[0].split(":")

//        if (data.status == false) {
//            itemView.approve_card.setBackgroundResource(R.drawable.bg_approve_reject)
//            itemView.type_text.text = "Pulang"
//            itemView.status_view.setImageResource(R.drawable.rejected)
//        }
        itemView.approve_name.text = data.fullname
        itemView.approve_nip.text = "NIP." + data.nip
        itemView.time_view.text = time[0] + ":" + time[1]
        itemView.remark_text.text = data.remark

//        if (context.spGetRole() == ConstVar.USR) {
//            itemView.approve_button.gone()
//            itemView.reject_button.gone()
//            itemView.status_view.visible()
//        }
    }

}
