package romara.rr.pdsidigitalabsensi.interfaces.login

import romara.rr.pdsidigitalabsensi.model.Location.MLocation

interface iLogs {

    fun onDataCompleteFromApi(q: MLocation)
    fun onDataErrorFromApi(throwable: Throwable)

}