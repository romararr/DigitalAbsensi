package romara.rr.pdsidigitalabsensi.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import romara.rr.pdsidigitalabsensi.model.absen.MAttend
import romara.rr.pdsidigitalabsensi.model.approval.MApprove
import romara.rr.pdsidigitalabsensi.model.location.MLocation
import romara.rr.pdsidigitalabsensi.model.user.MUser
import romara.rr.pdsidigitalabsensi.model.user.MUserLogin

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

    @POST("attendance/attend_all/")
    fun getApprovalAttend(@Body body: MutableMap<String, String>): Call<MApprove>

    @POST("attendance/do_approve/")
    fun approveAction(@Body body: MutableMap<String, String>): Call<MApprove>

}