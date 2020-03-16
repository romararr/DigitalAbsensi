package com.pdsi.digital.attendance.api

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import com.pdsi.digital.attendance.ext.spGetToken
import com.pdsi.digital.attendance.local.SharedPrefManager

class CustomInterceptor(contexts: Context) : Interceptor {

    var context: Context = contexts

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = context.spGetToken()
        var request = chain.request()

        if(token.isNotEmpty()) request = request.newBuilder().addHeader("Authorization", token).build()

        return chain.proceed(request)
    }
}