package ru.gb.lesson1.ui.main.model.marsApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsRoversApi {
    @GET("mars-photos/api/v1/rovers/perseverance/latest_photos")
    fun getLatestPhotoFromPerseverance(
        @Query("api_key") apiKey: String
    ) : Call<MarsRoverServerResponseDTO>

    @GET("mars-photos/api/v1/rovers/curiosity/latest_photos")
    fun getLatestPhotoFromCuriosity(
        @Query("api_key") apiKey: String
    ) : Call<MarsRoverServerResponseDTO>
}