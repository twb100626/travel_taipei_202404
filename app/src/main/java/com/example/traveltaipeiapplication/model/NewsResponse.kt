package com.example.traveltaipeiapplication.model

import com.google.gson.annotations.SerializedName;

data class NewsResponse(
        @SerializedName("total") val total: Int
        , @SerializedName("data") val data : Array<News>
)