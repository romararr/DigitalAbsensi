package romara.rr.pdsidigitalabsensi.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import romara.rr.pdsidigitalabsensi.model.Location.MLocation
import romara.rr.pdsidigitalabsensi.model.User.MLogin

interface APIServices {

    @POST("users/login")
    fun login(@Body body: MutableMap<String, String>): Call<MLogin>

    @POST("log/get_logging_personal")
    fun logAbsen(@Body body: MutableMap<String, String>): Call<MLocation>
}