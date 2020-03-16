package com.pdsi.digital.attendance.presenter

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.pdsi.digital.attendance.api.API
import com.pdsi.digital.attendance.ext.spGetPosid
import com.pdsi.digital.attendance.ext.spGetUser
import com.pdsi.digital.attendance.interfaces.approval.iApproval
import com.pdsi.digital.attendance.model.approval.MApprove

class ApprovePresenter(context: Context) {

    val iApprove = context as iApproval

    fun getApprovalData(context: Context, page: Int? = 1, search: String? = "", orderby: String? = "date_attend", sort: Int? = 1) {
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("pos_id", context.spGetPosid())
        requestBody.put("username", context.spGetUser())
        requestBody.put("search", search.toString())
        requestBody.put("limit", "100")
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

    fun approveUser(context: Context, id: Int, action: String) {
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("username", context.spGetUser())
        requestBody.put("id", id.toString())
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