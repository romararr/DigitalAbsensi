package romara.rr.pdsidigitalabsensi.model.approval

import com.google.gson.annotations.SerializedName

class MApproveUser(
        @SerializedName("idabsen")
        val id: String,
        @SerializedName("nip")
        val nip: String,
        @SerializedName("full_name")
        val fullname: String,
        @SerializedName("Tipe")
        val type: String,
        @SerializedName("date_attend")
        val date: String,
        @SerializedName("Jam_absen")
        val time: String,
        @SerializedName("Note")
        val remark: String
)
