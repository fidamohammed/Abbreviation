package com.example.abbreviation.ui.search

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.data.repository.Repository
import com.example.abbreviation.data.room.LongFormEntity
import com.example.abbreviation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repository: Repository, application: Application): AndroidViewModel(application) {

    private val _meaningLiveData : MutableLiveData<UiState> = MutableLiveData(UiState.Loading)
    val meaningLiveData: LiveData<UiState> get() = _meaningLiveData


    lateinit var readLongForm: LiveData<List<LongFormEntity>>

    //var readLongForm: LiveData<List<LongFormEntity>> = repository.readFromDb().asLiveData()

    fun getMeaningsFromDb(searchTerm: String){

        readLongForm= repository.readFromDb(searchTerm).asLiveData()
    }
    fun getMeaningsfromApi(sf: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getMeaningFromApi(sf)
            if(response.isEmpty()){
                _meaningLiveData.postValue(UiState.Error(Throwable(response.toString())))
            }else{
                _meaningLiveData.postValue(UiState.Success(response))
                for(longform in response){
                    saveApiDataIntoDb(longform)
                }
                //add to db
            }
        }

    }
//    fun isInDatabase(searchTerm: String): Boolean{
//        val dbData = repository.readFromDb(searchTerm)
//
//        return false
//    }

    fun saveApiDataIntoDb(longFormItemModel: LongFormItemModel){
        val longFormEntity = LongFormEntity(0,longFormItemModel.lfs,longFormItemModel.sf)
        //val longFormEntity = LongFormEntity(longFormItemModel)
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertIntoDb(longFormEntity)
        }
    }


    fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetWork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetWork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}