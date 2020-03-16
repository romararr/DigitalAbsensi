package com.pdsi.digital.attendance.model.user

import com.google.gson.annotations.SerializedName

data class MUserLogin(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("token")
    val token: String,
    @SerializedName("data")
    val data: MUserProfile
)