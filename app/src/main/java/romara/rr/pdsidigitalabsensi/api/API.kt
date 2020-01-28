package romara.rr.pdsidigitalabsensi.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import romara.rr.pdsidigitalabsensi.constants.ConstVar
import romara.rr.pdsidigitalabsensi.ext.spGetToken
import java.io.IOException
import java.util.concurrent.TimeUnit


class API {

    companion object {

        fun create(context: Context): APIServices {

            val httpInterceptor = HttpLoggingInterceptor()
            val interceptor = httpInterceptor.apply {
                httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(CustomInterceptor(context))
                    .retryOnConnectionFailure(true)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build()


            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ConstVar.DEV_URL)
//                    .baseUrl(ConstVar.BASE_URL)
//                    .baseUrl(ConstVar.LOCAL_URL)
                    .client(client)
                    .build()

            return retrofit.create(APIServices::class.java)
        }

    }

}