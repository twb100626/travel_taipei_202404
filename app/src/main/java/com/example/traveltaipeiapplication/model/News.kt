package com.example.traveltaipeiapplication.model

import com.google.gson.annotations.SerializedName;

data class News(
        @SerializedName("title") val title : String
        , @SerializedName("description") val description : String
        , @SerializedName("url") val url : String
)