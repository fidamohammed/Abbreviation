package com.example.abbreviation.data.repository

import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.data.room.LongFormDao
import com.example.abbreviation.data.room.LongFormEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface Repository{
    suspend fun getMeaningFromApi(sf: String) : ArrayList<LongFormItemModel>

    suspend fun insertIntoDb(longFormEntity: LongFormEntity)

    fun readFromDb(searchTerm: String): Flow<List<LongFormEntity>>

    fun readAllFromDb(): Flow<List<LongFormEntity>>
}