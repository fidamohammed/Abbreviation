package com.example.abbreviation.data.api

import com.example.abbreviation.data.model.LongFormItemModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDetails {

@GET("dictionary.py")
suspend fun getMeaning(@Query("sf") sf: String): ArrayList<LongFormItemModel>

}