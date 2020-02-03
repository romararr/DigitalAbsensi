package romara.rr.pdsidigitalabsensi.model.approval

import com.google.gson.annotations.SerializedName

class MApproveUser(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Id_user")
    val iduser: String,
    @SerializedName("nip")
    val nip: String,
    @SerializedName("full_name")
    val fullname: String,
    @SerializedName("Tipe")
    val type: String,
    @SerializedName("Status")
    val status: Boolean,
    @SerializedName("date_attend")
    val date: String,
    @SerializedName("Jam_absen")
    val time: String,
    @SerializedName("Note")
    val remark: String,
    @SerializedName("POS_ID")
    val posid: Int
)
