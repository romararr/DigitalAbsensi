package romara.rr.pdsidigitalabsensi.interfaces.home

import romara.rr.pdsidigitalabsensi.model.location.MLocation
import romara.rr.pdsidigitalabsensi.model.user.MUser

interface iHome {

    fun onDataCompleteFromApi(q: MLocation, type: String, resCondition: String)
    fun onGetAbsenCompleteApi(q: MUser)
    fun onDataErrorFromApi(throwable: Throwable)

}