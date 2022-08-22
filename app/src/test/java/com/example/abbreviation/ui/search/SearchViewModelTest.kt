package com.example.abbreviation.ui.search

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Database
import com.example.abbreviation.data.api.ApiDetails
import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.data.repository.Repository
import com.example.abbreviation.data.repository.RepositoryImpl
import com.example.abbreviation.data.room.LongFormDao
import com.example.abbreviation.data.room.LongFormDatabase
import com.example.abbreviation.getOrAwaitValue
import com.example.abbreviation.util.UiState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class SearchViewModelTest{

    lateinit var viewModel: SearchViewModel
    private lateinit var repositoryImpl: RepositoryImpl
    //private lateinit var db: LongFormDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiDetails: ApiDetails
    @Mock
    private lateinit var longFormDao: LongFormDao



//    @Before
//    fun setup(){
//        MockitoAnnotations.initMocks(this)
//        repositoryImpl = RepositoryImpl(apiDetails,longFormDao)
//        viewModel = SearchViewModel(repositoryImpl)
//    }
//
//    @Test
//    fun `data from api`()= runBlocking{
//        val searchTerm = "hmm"
//        Mockito.`when`(repositoryImpl.getMeaningFromApi(searchTerm))
//            .thenReturn(arrayListOf(LongFormItemModel(listOf(),searchTerm)))
//        viewModel.getMeaningsfromApi(searchTerm)
//        val result = viewModel.meaningLiveData.getOrAwaitValue()
//        assertEquals(result,UiState.Success(repositoryImpl.getMeaningFromApi(searchTerm)))
//    }
}