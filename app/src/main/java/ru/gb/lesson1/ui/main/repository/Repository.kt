package ru.gb.lesson1.ui.main.repository

import retrofit2.Callback
import ru.gb.lesson1.ui.main.model.marsApi.MarsRoverServerResponseDTO
import ru.gb.lesson1.ui.main.model.pictureOfTheDayApi.NasaServerResponseData

interface Repository {
    fun getDataFromServer(callback: Callback<NasaServerResponseData>, date: String)
    fun getDataFromCuriosity(callback: Callback<MarsRoverServerResponseDTO>)
    fun getDataFromPerseverance(callback: Callback<MarsRoverServerResponseDTO>)
}