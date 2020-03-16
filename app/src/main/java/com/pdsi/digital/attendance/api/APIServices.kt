package com.pdsi.digital.attendance.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.pdsi.digital.attendance.model.absen.MAttend
import com.pdsi.digital.attendance.model.approval.MApprove
import com.pdsi.digital.attendance.model.location.MLocation
import com.pdsi.digital.attendance.model.user.MUser
import com.pdsi.digital.attendance.model.user.MUserLogin

interface APIServices {

    @POST("users/login")
    fun login(@Body body: MutableMap<String, String>): Call<MUserLogin>

    @POST("users/detail")
    fun getUserDetail(@Body body: MutableMap<String, String>): Call<MUser>

//    @POST("log/logging/")
//    fun setLog(@Body body: MutableMap<String, String>): Call<MLogin>

    @POST("log/get_logging_personal/")
    fun logAbsen(@Body body: MutableMap<String, String>): Call<MLocation>

    @POST("attendance/attend/")
    fun attend(@Body body: MutableMap<String, String>): Call<MLocation>

    @POST("attendance/outoffice_attend/")
    fun attendOutOffice(@Body body: MutableMap<String, String>): Call<MLocation>

    @POST("attendance/attend_personal/")
    fun getPersonalAbsen(@Body body: MutableMap<String, String>): Call<MAttend>

    @POST("attendance/approval_all/")
    fun getApprovalAttend(@Body body: MutableMap<String, String>): Call<MApprove>

    @POST("attendance/do_approve/")
    fun approveAction(@Body body: MutableMap<String, String>): Call<MApprove>

}