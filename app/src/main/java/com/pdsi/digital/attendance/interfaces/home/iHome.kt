package com.pdsi.digital.attendance.interfaces.home

import com.pdsi.digital.attendance.model.location.MLocation
import com.pdsi.digital.attendance.model.user.MUser

interface iHome {

    fun onDataCompleteFromApi(q: MLocation, type: String, resCondition: String)
    fun onGetAbsenCompleteApi(q: MUser)
    fun onDataErrorFromApi(throwable: Throwable)

}