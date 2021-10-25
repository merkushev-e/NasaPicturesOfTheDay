package ru.gb.lesson1.ui.main.model

import retrofit2.Callback

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {


    override fun getDataFromServer(
        callback: Callback<NasaServerResponseData>,date: String
    ) {
        remoteDataSource.getDataFromNasa(callback, date)
    }

}