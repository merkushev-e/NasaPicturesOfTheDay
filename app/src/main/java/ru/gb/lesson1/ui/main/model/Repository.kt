package ru.gb.lesson1.ui.main.model

import retrofit2.Callback

interface Repository {
    fun getDataFromServer(callback: Callback<NasaServerResponseData>,date: String)

}