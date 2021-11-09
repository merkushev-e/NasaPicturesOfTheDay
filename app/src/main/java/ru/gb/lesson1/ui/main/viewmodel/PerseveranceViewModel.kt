package ru.gb.lesson1.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.lesson1.ui.main.model.marsApi.MarsRemoteDateSource
import ru.gb.lesson1.ui.main.model.marsApi.MarsRoverServerResponseDTO
import ru.gb.lesson1.ui.main.model.pictureOfTheDayApi.RemoteDataSource
import ru.gb.lesson1.ui.main.repository.Repository
import ru.gb.lesson1.ui.main.repository.RepositoryImpl


private const val SERVER_ERROR = "SERVER ERROR"
private const val REQUEST_ERROR = "REQUEST ERROR"

class PerseveranceViewModel : ViewModel() {
    private val repositoryImpl: Repository = RepositoryImpl(
        RemoteDataSource(),
        MarsRemoteDateSource()
    )

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    val liveData: LiveData<AppState> = liveDataToObserve

    fun getPerseveranceData() = getDataFromRemoteSourcePerseverance()

    private fun getDataFromRemoteSourcePerseverance(){
        liveDataToObserve.value = AppState.Loading(12)
        repositoryImpl.getDataFromPerseverance(callback)
    }

    private val callback = object : Callback<MarsRoverServerResponseDTO> {

        override fun onResponse(
            call: Call<MarsRoverServerResponseDTO>,
            response: Response<MarsRoverServerResponseDTO>
        ) {
            val serverResponseDTO: MarsRoverServerResponseDTO? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponseDTO != null) {
                    checkResponse(serverResponseDTO)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MarsRoverServerResponseDTO>, t: Throwable) {
            AppState.Error(Throwable(t.message ?: REQUEST_ERROR))
        }

        private fun checkResponse(serverResponse: MarsRoverServerResponseDTO): AppState {
            val nasaServerResponseDTO: MarsRoverServerResponseDTO = serverResponse
            return AppState.SuccessMarsRover(nasaServerResponseDTO)
        }
    }
}