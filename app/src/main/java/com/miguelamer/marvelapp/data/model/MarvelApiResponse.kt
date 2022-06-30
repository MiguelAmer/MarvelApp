package com.miguelamer.marvelapp.data.model

import com.google.gson.annotations.SerializedName

data class MarvelApiResponse(
        @SerializedName("code")
        val code: Int,

        @SerializedName("data")
        val data: MarvelApiData,

        @SerializedName("status")
        val status: String
    )
