package com.pdsi.digital.attendance.base.logs

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.logs_list_item_layout.view.*
import com.pdsi.digital.attendance.R
import com.pdsi.digital.attendance.base.BaseListAdapter
import com.pdsi.digital.attendance.model.location.MLogUser
import java.time.format.DateTimeFormatter

class RVLogsAdapter(private val listener: (position: Int) -> Unit) :
    BaseListAdapter<RecyclerView.ViewHolder, MLogUser>() {

    private var data = mutableListOf<MLogUser>()

    override fun setData(data: MutableList<MLogUser>) {
        this.data = data
        this.notifyDataSetChanged()
    }

    override fun getData(position: Int): MLogUser {
        return data[position]
    }

    override fun addData(data: MutableList<MLogUser>) {
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun getAll(): MutableList<MLogUser> {
        return this.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.logs_list_item_layout, parent, false), listener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is Holder -> holder.bindLocation(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    override fun removeItem(position: Int) {
        this.data.removeAt(position)
        this.notifyDataSetChanged()
    }

}

class Holder(v: View, listener: (position: Int) -> Unit) : RecyclerView.ViewHolder(v) {

    fun bindLocation(data: MLogUser) {
        val date = data.timing.split(" ")[0].split("-")

        itemView.log_date_absen.text = date[2] + "-" + date[1] + "-" + date[0]
        itemView.long_text.text = ": " + data.longitude.toString()
        itemView.lat_text.text = ": " + data.latitude.toString()
    }

}
