package romara.rr.pdsidigitalabsensi.base.attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.absen_list_item_layout.view.*
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.BaseListAdapter
import romara.rr.pdsidigitalabsensi.model.Attend

class AttendanceRecyclerView(private val listener: (position: Int) -> Unit) :
    BaseListAdapter<RecyclerView.ViewHolder, Attend>() {

    private var data = mutableListOf<Attend>()

    override fun setData(data: MutableList<Attend>) {
        this.data = data
        this.notifyDataSetChanged()
    }

    override fun getData(position: Int): Attend {
        return data[position]
    }

    override fun addData(data: MutableList<Attend>) {
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun getAll(): MutableList<Attend> {
        return this.data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return Holder(inflater.inflate(R.layout.absen_list_item_layout, parent, false), listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is Holder -> holder.bindAttend(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

}

class Holder(v: View, listener: (position: Int) -> Unit) : RecyclerView.ViewHolder(v) {

    fun bindAttend(data: Attend) {
        itemView.time_masuk.text = data.time_in
        itemView.time_pulang.text = data.time_out
        itemView.date_absen.text = data.date_attend
    }

}
