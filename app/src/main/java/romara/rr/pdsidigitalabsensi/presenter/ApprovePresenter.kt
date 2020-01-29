package romara.rr.pdsidigitalabsensi.presenter

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import romara.rr.pdsidigitalabsensi.api.API
import romara.rr.pdsidigitalabsensi.ext.spGetPosid
import romara.rr.pdsidigitalabsensi.ext.spGetUser
import romara.rr.pdsidigitalabsensi.interfaces.approval.iApproval
import romara.rr.pdsidigitalabsensi.model.approval.MApprove

class ApprovePresenter(context: Context) {

    val iApprove = context as iApproval

    fun getApprovalData(context: Context, page: Int, search: String? = "", orderby: String? = "date_attend", sort: String? = "ASC") {
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("pos_id", context.spGetPosid())
        requestBody.put("username", context.spGetUser())
        requestBody.put("search", search.toString())
        requestBody.put("limit", "20")
        requestBody.put("page", page.toString())
        requestBody.put("order_by", orderby.toString())
        requestBody.put("sort", sort.toString())

        API.create(context).getApprovalAttend(requestBody)
                .enqueue(object : Callback<MApprove> {
                    override fun onFailure(call: Call<MApprove>, t: Throwable) {
                        iApprove.onDataErrorFromApi(t)
                    }

                    override fun onResponse(call: Call<MApprove>, response: Response<MApprove>) {
                        iApprove.onDataCompleteFromApi(response.body() as MApprove)
                        Log.d("APPROVAL DATA", response.body().toString())
                    }
                })
    }

    fun approveUser(context: Context, id: String, action: String) {
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", context.spGetUser())
        requestBody.put("id", id)
        requestBody.put("action", action)

        API.create(context).approveAction(requestBody)
                .enqueue(object : Callback<MApprove> {
                    override fun onFailure(call: Call<MApprove>, t: Throwable) {
                        iApprove.onDataErrorFromApi(t)
                    }

                    override fun onResponse(call: Call<MApprove>, response: Response<MApprove>) {
                        iApprove.onDataCompleteFromApi(response.body() as MApprove)
                        Log.d("APPROVAL RES", response.body().toString())
                    }
                })
    }


}