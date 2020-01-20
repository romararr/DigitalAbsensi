package romara.rr.pdsidigitalabsensi.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class API {

    companion object {

        val BASE_URL  = "https://apps.pertamina.com/PDSIDAS/api/"
        val LOCAL = "http://10.13.1.55/PDSIDAS/apidev/"

        fun create(context: Context): APIServices {

            val httpInterceptor = HttpLoggingInterceptor()
            val interceptor = httpInterceptor.apply {
                httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(CustomInterceptor(context))
                .retryOnConnectionFailure(true)
                .build()

            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(LOCAL)
                .client(client)
                .build()

            return retrofit.create(APIServices::class.java)
        }

    }

}