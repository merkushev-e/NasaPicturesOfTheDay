package ru.gb.lesson1.ui.main.repository

import retrofit2.Callback
import ru.gb.lesson1.ui.main.model.marsApi.MarsRemoteDateSource
import ru.gb.lesson1.ui.main.model.marsApi.MarsRoverServerResponseDTO
import ru.gb.lesson1.ui.main.model.pictureOfTheDayApi.NasaServerResponseData
import ru.gb.lesson1.ui.main.model.pictureOfTheDayApi.RemoteDataSource

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val marsDataSource: MarsRemoteDateSource,

    ) : Repository {


    override fun getDataFromServer(
        callback: Callback<NasaServerResponseData>, date: String
    ) {
        remoteDataSource.getDataFromNasa(callback, date)
    }

    override fun getDataFromCuriosity(callback: Callback<MarsRoverServerResponseDTO>) {
        marsDataSource.getDataFromCuriosity(callback)
    }

    override fun getDataFromPerseverance(callback: Callback<MarsRoverServerResponseDTO>) {
        marsDataSource.getDataFromPerseverance(callback)

    }


}