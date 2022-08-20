package com.example.abbreviation.data.room

import androidx.room.TypeConverter
import com.example.abbreviation.data.model.LfModel
import com.example.abbreviation.data.model.LongFormItemModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LongFormTypeConverter {

    var gson = Gson()

//    @TypeConverter
//    fun longFormToString(longFormItemModel: LongFormItemModel): String = gson.toJson(longFormItemModel)
//
//    @TypeConverter
//    fun stringToLongForm(data: String): LongFormItemModel{
//        val listType = object : TypeToken<LongFormItemModel>() {}.type
//        return gson.fromJson(data, listType)
//    }
    @TypeConverter
    fun longToString(longForm: List<LfModel>): String = gson.toJson(longForm)

    @TypeConverter
    fun stringToLong(data: String): List<LfModel>{
        val listType = object : TypeToken<List<LfModel>>() {}.type
        return gson.fromJson(data, listType)
    }

}