package romara.rr.pdsidigitalabsensi.interfaces.home

import romara.rr.pdsidigitalabsensi.model.Attend
import romara.rr.pdsidigitalabsensi.model.Location.MLocation

interface iHome {

    fun onDataCompleteFromApi(q: MLocation)
    fun onDataErrorFromApi(throwable: Throwable)

}