package ru.gb.lesson1.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.lesson1.ui.main.model.marsApi.MarsRemoteDateSource
import ru.gb.lesson1.ui.main.model.pictureOfTheDayApi.NasaServerResponseData
import ru.gb.lesson1.ui.main.model.pictureOfTheDayApi.RemoteDataSource
import ru.gb.lesson1.ui.main.repository.Repository
import ru.gb.lesson1.ui.main.repository.RepositoryImpl
import java.text.SimpleDateFormat
import java.util.*

private const val SERVER_ERROR = "SERVER ERROR"
private const val REQUEST_ERROR = "REQUEST ERROR"
private const val YESTERDAY = -1
private const val BEFORE_YESTERDAY = -2


class MainViewModel : ViewModel() {

    private val repositoryImpl: Repository = RepositoryImpl(RemoteDataSource(),
        MarsRemoteDateSource()
    )
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    val liveData: LiveData<AppState> = liveDataToObserve

    private var currentDate: String = convertDate(Date().time)

    private fun getPreviousDate(amount: Int): Long{
         val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, amount)
        return calendar.timeInMillis
    }

    fun getData() = getDataFromRemoteSource(currentDate)
    fun getYesterdayData() = getDataFromRemoteSource(convertDate(getPreviousDate(YESTERDAY)))
    fun getBeforeYesterdayData() = getDataFromRemoteSource(convertDate(getPreviousDate(BEFORE_YESTERDAY)))


    private fun getDataFromRemoteSource(date: String){
        liveDataToObserve.value = AppState.Loading(12)
        repositoryImpl.getDataFromServer(callback,date)

    }

    private val callback = object : Callback<NasaServerResponseData> {

        override fun onResponse(
            call: Call<NasaServerResponseData>,
            response: Response<NasaServerResponseData>
        ) {
            val serverResponseData: NasaServerResponseData? = response.body()
            liveDataToObserve.postValue(
                if(response.isSuccessful && serverResponseData != null){
                    checkResponse(serverResponseData)
                }else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<NasaServerResponseData>, t: Throwable) {
            AppState.Error(Throwable(t.message ?: REQUEST_ERROR))
        }


        private fun checkResponse(serverResponse: NasaServerResponseData): AppState {
            val nasaServerResponseData: NasaServerResponseData = serverResponse
            return AppState.Success(nasaServerResponseData)
        }

    }

    private fun convertDate(date: Long): String{
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    }
}