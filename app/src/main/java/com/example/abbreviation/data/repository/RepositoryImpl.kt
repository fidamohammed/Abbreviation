package com.example.abbreviation.data.repository

import com.example.abbreviation.data.api.ApiDetails
import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.data.room.LongFormDao
import com.example.abbreviation.data.room.LongFormEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val apiDetails: ApiDetails, val longFormDao: LongFormDao): Repository {
    override suspend fun getMeaningFromApi(sf: String): ArrayList<LongFormItemModel> {
        return apiDetails.getMeaning(sf)
    }

    override suspend fun insertIntoDb(longFormEntity: LongFormEntity) =
        longFormDao.insertIntoDb(longFormEntity)

    override fun readFromDb(searchTerm: String): Flow<List<LongFormEntity>> =
        longFormDao.readFromDb(searchTerm)

    override fun readAllFromDb(): Flow<List<LongFormEntity>> =
        longFormDao.readAllFromDb()

}