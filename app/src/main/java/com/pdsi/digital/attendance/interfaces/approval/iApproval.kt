package com.pdsi.digital.attendance.interfaces.approval

import com.pdsi.digital.attendance.model.approval.MApprove

interface iApproval {

    fun onDataCompleteFromApi(q: MApprove)
    fun onDataErrorFromApi(throwable: Throwable)

}
