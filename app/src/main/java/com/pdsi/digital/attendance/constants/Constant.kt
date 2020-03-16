package com.pdsi.digital.attendance.constants

import com.pdsi.digital.attendance.model.approval.MApproveUser
import com.pdsi.digital.attendance.model.location.MLocation
import com.pdsi.digital.attendance.model.location.MLogUser

object Constant {

    fun emptyAttendRes(): MLocation {
        return MLocation(true, "", "", 0, 0, 0, 0, 0, emptyUserData())
    }

    fun emptyUserData(): MutableList<MLogUser> {
        return mutableListOf(
                MLogUser("", "", "", "", 0.0, 0.0)
        )
    }

    fun dataLogAbsen(): MutableList<MLogUser> {
        return mutableListOf(
                MLogUser("205", "1238123", "yoga.hermawan", "2020-01-20 07:45:08.000", -6.3891, 106.8583),
                MLogUser("205", "1238123", "yoga.hermawan", "2020-01-20 07:45:08.000", -6.3891, 106.8583)
        )
    }

    fun dataApproval(): MutableList<MApproveUser> {
        return mutableListOf(
                MApproveUser(0, "0", "12345678", "Midoriya Izuku", "come", true, "2020-01-20", "07:32", "Saya sedang ada dikantor cabang untuk keperluan meeting bersama Dirut", 0),
                MApproveUser(1, "1", "32872392", "Light Yagami", "come", true, "2020-01-20", "07:21", "Saya sedang ada dikantor cabang untuk keperluan meeting bersama Dirut", 0),
                MApproveUser(2, "2", "12390839", "Sarada Uchiha", "return", false, "2020-01-20", "17:32", "Saya sedang ada dikantor cabang untuk keperluan meeting bersama Dirut", 0),
                MApproveUser(3, "3", "32498501", "Nobi Nobita", "come", true, "2020-01-20", "07:41", "Saya sedang ada dikantor cabang untuk keperluan meeting bersama Dirut", 0),
                MApproveUser(4, "4", "92390340", "Kazuki Ito", "return", true, "2020-01-20", "16:41", "Saya sedang ada dikantor cabang untuk keperluan meeting bersama Dirut", 0)
        )
    }
}