package com.example.abbreviation.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LongFormDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoDb(longFormEntity: LongFormEntity)

    @Query("Select * from longform where sf like :searchTerm")
    fun readFromDb(searchTerm: String): Flow<List<LongFormEntity>>

    @Query("Select * from longform")
    fun readAllFromDb(): Flow<List<LongFormEntity>>

}