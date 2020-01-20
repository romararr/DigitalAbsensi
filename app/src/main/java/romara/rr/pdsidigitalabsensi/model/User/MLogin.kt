package romara.rr.pdsidigitalabsensi.model.User

import com.google.gson.annotations.SerializedName

data class MLogin(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("data")
    val data: MUserProfile
)