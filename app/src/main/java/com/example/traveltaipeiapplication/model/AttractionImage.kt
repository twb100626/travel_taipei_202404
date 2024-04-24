package com.example.traveltaipeiapplication.model

import com.google.gson.annotations.SerializedName;

data class AttractionImage(
        @SerializedName("src") val src : String
        , @SerializedName("ext") val ext : String
)