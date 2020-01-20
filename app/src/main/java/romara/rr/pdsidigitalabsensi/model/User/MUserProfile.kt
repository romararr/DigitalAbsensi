package romara.rr.pdsidigitalabsensi.model.User

import com.google.gson.annotations.SerializedName

data class MUserProfile(
    @SerializedName("username")
    val username: String,
    @SerializedName("full_name")
    val fullname: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("nip")
    val nip: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nohp")
    val nohp: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("pos_id")
    val posid: String,
    @SerializedName("status")
    val status: String
)