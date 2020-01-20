package romara.rr.pdsidigitalabsensi.interfaces.absensi

import romara.rr.pdsidigitalabsensi.model.Attend

interface iAbsensi {
    fun onDataCompleteFromApi(q: Attend)
    fun onDataErrorFromApi(throwable: Throwable)
}