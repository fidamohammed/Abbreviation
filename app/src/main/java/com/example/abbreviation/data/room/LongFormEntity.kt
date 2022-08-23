package com.example.abbreviation.data.room

import androidx.room.*
import com.example.abbreviation.data.model.LfModel
import com.example.abbreviation.data.model.LongFormItemModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable


//@Entity(tableName = "LongForm")
//class LongFormEntity(val longForm: LongFormItemModel) {
//
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
//
//}

@Entity(tableName = "LongForm")
data class LongFormEntity(
    @PrimaryKey
    val sf: String,
    @TypeConverters(LongFormTypeConverter::class)
    val lfs: List<LfModel>

) : Serializable