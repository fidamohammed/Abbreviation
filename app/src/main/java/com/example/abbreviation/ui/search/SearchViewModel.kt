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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repository: Repository): ViewModel() {

    private val _meaningLiveData : MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val meaningLiveData: StateFlow<UiState> get() = _meaningLiveData


    lateinit var readLongForm: LiveData<List<LongFormEntity>>

    //var readLongForm: LiveData<List<LongFormEntity>> = repository.readFromDb().asLiveData()

    fun getMeaningsFromDb(searchTerm: String){
        readLongForm= repository.readFromDb(searchTerm).asLiveData()
    }
//    fun resetFlow(){
//        _meaningLiveData.value = UiState.Loading
//    }

    fun getMeaningsfromApi(sf: String){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = repository.getMeaningFromApi(sf)
                if(response.isEmpty()){
                    _meaningLiveData.value = UiState.Error("")
                }else{
                    _meaningLiveData.value = UiState.Success(response)
                    for(longform in response){
                        saveApiDataIntoDb(longform)
                    }
                }
            }catch (e: Exception){
                _meaningLiveData.value = UiState.Error("Error ${e.message}")
            }

        }

    }

    fun saveApiDataIntoDb(longFormItemModel: LongFormItemModel){
        val longFormEntity = LongFormEntity(longFormItemModel.sf,longFormItemModel.lfs)
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertIntoDb(longFormEntity)
        }
    }


//    fun hasInternetConnection(): Boolean {
//        val connectivityManager = getApplication<Application>().getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//        val activeNetWork = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(activeNetWork) ?: return false
//
//        return when {
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }
//    }
}