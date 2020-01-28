package romara.rr.pdsidigitalabsensi.interfaces.absensi

import romara.rr.pdsidigitalabsensi.model.absen.MAttend

interface iAbsensi {
    fun onDataCompleteFromApi(q: MAttend)
    fun onDataErrorFromApi(throwable: Throwable)
}