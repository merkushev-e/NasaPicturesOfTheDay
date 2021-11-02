package ru.gb.lesson1.ui.main.viewmodel

import ru.gb.lesson1.ui.main.model.NasaServerResponseData

sealed class AppState {
    data class Success(val serverResponseData: NasaServerResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?): AppState()
}