package com.pdsi.digital.attendance.model.location

import com.google.gson.annotations.SerializedName

data class MLogUser(
    @SerializedName("Id_User")
    var userid: String,
    @SerializedName("nip")
    var nip: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("timing")
    val timing: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)