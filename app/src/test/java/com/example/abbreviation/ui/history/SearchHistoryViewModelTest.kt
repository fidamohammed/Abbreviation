package com.example.abbreviation.ui.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.abbreviation.data.repository.FakeRepository
import com.example.abbreviation.data.room.LongFormEntity
import com.example.abbreviation.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SearchHistoryViewModelTest{

    lateinit var repository: FakeRepository
    lateinit var searchHistoryViewModel: SearchHistoryViewModel

    val dispatcher = TestCoroutineDispatcher()
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
        repository = FakeRepository()
        searchHistoryViewModel = SearchHistoryViewModel(repository)
    }

    @Test
    fun `getWordsFromDb with data`() = runBlocking{
        repository.list.apply {
            add(LongFormEntity("entry1", listOf()))
            add(LongFormEntity("entry2", listOf()))
        }
        searchHistoryViewModel.getWordsFromDb()
//        val result = searchHistoryViewModel.readLongForm.getOrAwaitValue()
//        assertEquals(listOf(LongFormEntity("entry1", listOf()),LongFormEntity("entry2", listOf())),result)

        searchHistoryViewModel.readLongForm.observeForever {
            assertEquals(listOf(LongFormEntity("entry1", listOf()),LongFormEntity("entry2", listOf())),it)
        }

    }

    @Test
    fun `getWordsFromDb with empty db`(){
        searchHistoryViewModel.getWordsFromDb()
        val result = searchHistoryViewModel.readLongForm.getOrAwaitValue()
        assertEquals(listOf<LongFormEntity>(),result)
    }

}