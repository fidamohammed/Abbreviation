package com.example.abbreviation.ui.history

import androidx.lifecycle.*
import com.example.abbreviation.data.repository.Repository
import com.example.abbreviation.data.room.LongFormEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(val repository: Repository): ViewModel() {

    //var readLongForm: LiveData<List<LongFormEntity>> = repository.readAllFromDb().asLiveData()

    lateinit var history: ArrayList<String>

    lateinit var readLongForm: LiveData<List<LongFormEntity>>

    //var readLongForm: LiveData<List<LongFormEntity>> = repository.readFromDb().asLiveData()

    fun getWordsFromDb(){
        readLongForm= repository.readAllFromDb().asLiveData()
    }


}