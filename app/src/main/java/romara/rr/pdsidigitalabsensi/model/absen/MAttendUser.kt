package romara.rr.pdsidigitalabsensi.model.absen

import com.google.gson.annotations.SerializedName

data class MAttendUser(
    @SerializedName("username")
    var username: String,
    @SerializedName("nip")
    var nip: String,
    @SerializedName("full_name")
    var fullname: String,
    @SerializedName("date_attend")
    var date_attend: String,
    @SerializedName("time_come")
    var time_come: String,
    @SerializedName("time_return")
    var time_return: String,
    @SerializedName("time_break")
    var time_break: String,
    @SerializedName("time_end_of_break")
    var time_endbreak: String,
    @SerializedName("note")
    var note: String,
    @SerializedName("mark")
    var mark: String
)
