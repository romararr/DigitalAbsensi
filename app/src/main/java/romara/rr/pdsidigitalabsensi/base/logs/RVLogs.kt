package romara.rr.pdsidigitalabsensi.base.logs

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.absen_list_item_layout.view.*
import kotlinx.android.synthetic.main.logs_list_item_layout.view.*
import romara.rr.pdsidigitalabsensi.R
import romara.rr.pdsidigitalabsensi.base.BaseListAdapter
import romara.rr.pdsidigitalabsensi.ext.getCompleteAdress
import romara.rr.pdsidigitalabsensi.model.Location.MLogUser
import java.text.SimpleDateFormat
import java.time.LocalDateTime

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

}

class Holder(v: View, listener: (position: Int) -> Unit) : RecyclerView.ViewHolder(v) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bindLocation(data: MLogUser) {
        itemView.log_date_absen.text = SimpleDateFormat("DD MM YYYY").format(LocalDateTime.parse(data.timing))
        itemView.lat_text.text = data.latitude.toString()
        itemView.long_text.text = data.longitude.toString()
//        itemView.log_location_text = Context..getCompleteAdress(act., data.latitude, data.longitude)
    }

}
