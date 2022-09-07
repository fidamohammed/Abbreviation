package com.example.abbreviation.data.repository

import com.example.abbreviation.data.api.ApiDetails
import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.data.room.LongFormDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class RepositoryImplTest{

    lateinit var repositoryImpl: RepositoryImpl

    @Mock
    lateinit var apiDetails: ApiDetails

    @Mock
    lateinit var longFormDao: LongFormDao

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        repositoryImpl = RepositoryImpl(apiDetails,longFormDao)
    }

    @Test
    fun `getMeaningFromApi`() = runBlocking{
        whenever(apiDetails.getMeaning("test")).thenReturn(arrayListOf(LongFormItemModel(listOf(),"test")))
        val result = repositoryImpl.getMeaningFromApi("test")
        assertEquals(arrayListOf(LongFormItemModel(listOf(),"test")),result)
    }

}