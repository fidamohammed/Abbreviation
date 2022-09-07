package com.example.abbreviation.ui.search

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Database
import com.example.abbreviation.data.api.ApiDetails
import com.example.abbreviation.data.model.LfModel
import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.data.repository.FakeRepository
import com.example.abbreviation.data.repository.Repository
import com.example.abbreviation.data.repository.RepositoryImpl
import com.example.abbreviation.data.room.LongFormDao
import com.example.abbreviation.data.room.LongFormDatabase
import com.example.abbreviation.data.room.LongFormEntity
import com.example.abbreviation.getOrAwaitValue
import com.example.abbreviation.util.UiState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


class SearchViewModelTest {

    val dispatcher = TestCoroutineDispatcher()
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: SearchViewModel
    private lateinit var repository: FakeRepository


    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repository = FakeRepository()
        viewModel = SearchViewModel(repository)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `saveApiDataIntoDb`() = runBlocking{

        viewModel.saveApiDataIntoDb(LongFormItemModel(listOf(), "test"))
        delay(2000)
        val dbData = repository.list
        //val dbData = repository.readAllFromDb().asLiveData()
        assertEquals(listOf(LongFormEntity("test", listOf())), dbData)
        //assertEquals(listOf(LongFormItemModel(listOf(),"test")),dbData)
    }


    @Test
    fun `getMeaningsFromDb with data`() = runBlocking{
        viewModel.saveApiDataIntoDb(LongFormItemModel(listOf(), "test1"))
        delay(2000)
        viewModel.getMeaningsFromDb("test1")
        viewModel.readLongForm.observeForever {
            assertEquals(listOf(LongFormEntity("test1", listOf())),it)
            //assertEquals(repository.list,it)
        }
    }

    @Test
    fun `getMeaningFromDb with empty db`(){
        viewModel.getMeaningsFromDb("test")
        viewModel.readLongForm.observeForever {
            assertEquals(emptyList<LongFormEntity>(),it)
        }
    }

    @Test
    fun `getMeaningFromApi with data`() = runBlocking{
        viewModel.getMeaningsfromApi("sample")
        //delay(4000)
        var result = viewModel.meaningLiveData.asLiveData().getOrAwaitValue()
        assertEquals(UiState.Success(arrayListOf(LongFormItemModel(listOf(),"sample"))),result)
        //assertEquals(UiState.Success(repository.list),result)
    }

    @Test
    fun `getMeaningFromApi with empty list`(){
        viewModel.getMeaningsfromApi("")
        var result = viewModel.meaningLiveData.asLiveData().getOrAwaitValue()
        assertEquals(UiState.Error(""),result)

    }

//    @Mock
//    private lateinit var apiDetails: ApiDetails
//    @Mock
//    private lateinit var longFormDao: LongFormDao


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