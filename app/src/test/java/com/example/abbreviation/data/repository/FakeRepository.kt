package com.example.abbreviation.data.repository

import com.example.abbreviation.data.api.ApiDetails
import com.example.abbreviation.data.model.LfModel
import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.data.room.LongFormEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mock

class FakeRepository: Repository {

    var list = mutableListOf<LongFormEntity>()

    override suspend fun getMeaningFromApi(sf: String): ArrayList<LongFormItemModel> {
        //return arrayListOf(LongFormItemModel(listOf(),sf))
        return arrayListOf()
    }

    override suspend fun insertIntoDb(longFormEntity: LongFormEntity) {
        list.add(longFormEntity)

    }

    override fun readFromDb(searchTerm: String): Flow<List<LongFormEntity>> {
        var resultList = mutableListOf<LongFormEntity>()
        for(item in list){
            if(item.sf == searchTerm){
                resultList.add(item)
            }
        }
        return flowOf(resultList)
    }

    override fun readAllFromDb(): Flow<List<LongFormEntity>> {
        return flowOf(list)
    }
}