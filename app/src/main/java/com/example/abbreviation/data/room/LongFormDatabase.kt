package com.example.abbreviation.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [LongFormEntity::class], version = 1, exportSchema = false)
@TypeConverters(LongFormTypeConverter::class)
abstract class LongFormDatabase: RoomDatabase() {

    abstract fun longFormDao() : LongFormDao

}