package com.example.traveltaipeiapplication.model

import com.google.gson.annotations.SerializedName;

data class AttractionResponse(
        @SerializedName("total") val total: Int
        , @SerializedName("data") val data : Array<Attraction>
)