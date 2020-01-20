package romara.rr.pdsidigitalabsensi.model.Location

import com.google.gson.annotations.SerializedName

data class MLocation(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("total_row")
    val row: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("now_data_show")
    val nowData: Int,
    @SerializedName("total_page")
    val pageTotal: Int,
    @SerializedName("page_now")
    val page: Int,
    @SerializedName("data")
    val data: MutableList<MLogUser>?
)