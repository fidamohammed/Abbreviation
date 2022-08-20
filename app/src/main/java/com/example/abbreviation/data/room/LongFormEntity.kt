package com.example.abbreviation.data.room

import androidx.room.*
import com.example.abbreviation.data.model.LfModel
import com.example.abbreviation.data.model.LongFormItemModel
import com.google.gson.annotations.SerializedName


//@Entity(tableName = "LongForm")
//class LongFormEntity(val longForm: LongFormItemModel) {
//
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
//
//}

@Entity(tableName = "LongForm")
data class LongFormEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @TypeConverters(LongFormTypeConverter::class)
    val lfs: List<LfModel>,
    val sf: String
)