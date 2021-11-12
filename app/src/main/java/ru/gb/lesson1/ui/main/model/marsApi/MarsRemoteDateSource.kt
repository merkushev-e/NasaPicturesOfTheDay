package ru.gb.lesson1.ui.main.model.marsApi

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.lesson1.BuildConfig
import java.io.IOException

class MarsRemoteDateSource {

    private val baseUrl = "https://api.nasa.gov/"
    val marsRoverRetrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(createOkHttpClient(MRInterceptor()))
        .build().create(MarsRoversApi::class.java)

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    inner class MRInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }

    fun getDataFromCuriosity(callback: Callback<MarsRoverServerResponseDTO>){
        marsRoverRetrofit.getLatestPhotoFromCuriosity(BuildConfig.NASA_API_KEY).enqueue(callback)
    }

    fun getDataFromPerseverance(callback: Callback<MarsRoverServerResponseDTO>){
        marsRoverRetrofit.getLatestPhotoFromPerseverance(BuildConfig.NASA_API_KEY).enqueue(callback)

    }
}