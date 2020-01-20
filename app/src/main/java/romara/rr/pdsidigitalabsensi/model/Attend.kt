package romara.rr.pdsidigitalabsensi.model

import com.google.gson.annotations.SerializedName

data class Attend(
    @SerializedName("time_come")
    val time_in: String,
    @SerializedName("time_return")
    val time_out: String,
    @SerializedName("date_attend")
    val date_attend: String
)