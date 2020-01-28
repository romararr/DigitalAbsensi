package romara.rr.pdsidigitalabsensi.interfaces.approval

import romara.rr.pdsidigitalabsensi.model.approval.MApprove

interface iApproval {

    fun onDataCompleteFromApi(q: MApprove)
    fun onDataErrorFromApi(throwable: Throwable)

}
