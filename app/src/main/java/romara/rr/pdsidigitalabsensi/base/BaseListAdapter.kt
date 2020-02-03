package romara.rr.pdsidigitalabsensi.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<H, T> : RecyclerView.Adapter<H>() where H : RecyclerView.ViewHolder {
    abstract fun setData(data: MutableList<T>)
    abstract fun getData(position: Int): T
    abstract fun addData(data: MutableList<T>)
    abstract fun getAll(): MutableList<T>
    abstract fun removeItem(position: Int)
}