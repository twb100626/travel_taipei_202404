package com.example.traveltaipeiapplication.model

import com.google.gson.annotations.SerializedName

data class Attraction (
        @SerializedName("name") val name : String
        , @SerializedName("introduction") val introduction : String
        , @SerializedName("open_time") val open_time : String
        , @SerializedName("address") val address : String
        , @SerializedName("tel") val tel : String
        , @SerializedName("remind") val remind : String
        , @SerializedName("url") val url : String
        , @SerializedName("images") val images : Array<AttractionImage>
)