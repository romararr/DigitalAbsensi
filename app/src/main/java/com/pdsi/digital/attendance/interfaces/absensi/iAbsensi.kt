package com.pdsi.digital.attendance.interfaces.absensi

import com.pdsi.digital.attendance.model.absen.MAttend

interface iAbsensi {
    fun onDataCompleteFromApi(q: MAttend)
    fun onDataErrorFromApi(throwable: Throwable)
}