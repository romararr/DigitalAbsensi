package com.pdsi.digital.attendance.interfaces.login

import com.pdsi.digital.attendance.model.location.MLocation

interface iLogs {

    fun onDataCompleteFromApi(q: MLocation)
    fun onDataErrorFromApi(throwable: Throwable)

}