package romara.rr.pdsidigitalabsensi.interfaces.login

import romara.rr.pdsidigitalabsensi.model.user.MUserLogin

interface iLogin {

    fun onLoading(status: Boolean? = false)
    fun onDataCompleteFromApi(q: MUserLogin)
    fun onDataErrorFromApi(throwable: Throwable)

}