package romara.rr.pdsidigitalabsensi.interfaces.login

import romara.rr.pdsidigitalabsensi.model.User.MLogin

interface iLogin {

    fun onDataCompleteFromApi(q: MLogin)
    fun onDataErrorFromApi(throwable: Throwable)

}