package com.pdsi.digital.attendance.interfaces.login

import com.pdsi.digital.attendance.model.user.MUserLogin

interface iLogin {

    fun onLoading(status: Boolean? = false)
    fun onDataCompleteFromApi(q: MUserLogin)
    fun onDataErrorFromApi(throwable: Throwable)

}