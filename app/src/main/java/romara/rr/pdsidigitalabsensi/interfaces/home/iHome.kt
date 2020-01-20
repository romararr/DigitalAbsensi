package romara.rr.pdsidigitalabsensi.interfaces.home

import romara.rr.pdsidigitalabsensi.model.Attend

interface iHome {

    fun onDataCompleteFromApi(q: Attend)
    fun onDataErrorFromApi(throwable: Throwable)

}