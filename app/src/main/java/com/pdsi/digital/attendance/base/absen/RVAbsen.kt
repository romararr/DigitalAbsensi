package com.pdsi.digital.attendance.base.absen

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.absen_list_item_layout.view.*
import com.pdsi.digital.attendance.R
import com.pdsi.digital.attendance.base.BaseListAdapter
import com.pdsi.digital.attendance.model.absen.MAttendUser

class RVAbsenAdapter(private val listener: (position: Int) -> Unit) :
    BaseListAdapter<RecyclerView.ViewHolder, MAttendUser>() {

    private var data = mutableListOf<MAttendUser>()

    override fun setData(data: MutableList<MAttendUser>) {
        this.data = data
        this.notifyDataSetChanged()
    }

    override fun getData(position: Int): MAttendUser {
        return data[position]
    }

    override fun addData(data: MutableList<MAttendUser>) {
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun getAll(): MutableList<MAttendUser> {
        return this.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.absen_list_item_layout, parent, false), listener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is Holder -> holder.bindAttend(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    override fun removeItem(position: Int) {
        this.data.removeAt(position)
        this.notifyDataSetChanged()
    }

}

class Holder(v: View, listener: (position: Int) -> Unit) : RecyclerView.ViewHolder(v) {
    fun bindAttend(data: MAttendUser) {

        val date = data.date_attend.split("-")

        itemView.time_masuk.text = ": " +
                if (data.time_come.isNullOrEmpty()) "--:--"
                else data.time_come.split(".")[0]
        itemView.time_pulang.text = ": " +
                if (data.time_return.isNullOrEmpty()) "--:--"
                else data.time_return.split(".")[0]
        itemView.date_absen.text = date[2] + "-" + date[1] + "-" + date[0]
    }

}
