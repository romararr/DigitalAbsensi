package romara.rr.pdsidigitalabsensi.model.user

import com.google.gson.annotations.SerializedName

data class MUser(
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: Boolean,
        @SerializedName("data")
        val data: MutableList<MUserProfile>
)