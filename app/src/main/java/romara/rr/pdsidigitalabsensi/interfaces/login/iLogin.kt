package romara.rr.pdsidigitalabsensi.interfaces.login

import romara.rr.pdsidigitalabsensi.model.user.MUserLogin

interface iLogin {

    fun onDataCompleteFromApi(q: MUserLogin)
    fun onDataErrorFromApi(throwable: Throwable)

}