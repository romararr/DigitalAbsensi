package romara.rr.pdsidigitalabsensi.api

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import romara.rr.pdsidigitalabsensi.ext.spGetToken
import romara.rr.pdsidigitalabsensi.local.SharedPrefManager

class CustomInterceptor(contexts: Context): Interceptor {

    var context: Context = contexts

    override fun intercept(chain: Interceptor.Chain): Response {
        val token =  SharedPrefManager.getInstance(context).getToken()
        var request = chain.request()
        request = request.newBuilder().addHeader("Authorization", token.toString()).build()

        return chain.proceed(request)
    }
}