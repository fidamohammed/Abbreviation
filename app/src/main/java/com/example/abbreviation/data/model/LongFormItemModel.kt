package com.example.abbreviation.data.model


import com.google.gson.annotations.SerializedName

data class LongFormItemModel(
    @SerializedName("lfs")
    val lfs: List<LfModel>,
    @SerializedName("sf")
    val sf: String
)