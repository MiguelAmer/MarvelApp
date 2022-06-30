package com.miguelamer.marvelapp.data.network

import com.miguelamer.marvelapp.data.model.MarvelApiResponse
import com.miguelamer.marvelapp.data.model.MarvelCharacterModel
import com.miguelamer.marvelapp.data.network.MarvelApiConstants.MARVEL_API_HASH
import com.miguelamer.marvelapp.data.network.MarvelApiConstants.MARVEL_API_PUBLIC_KEY
import com.miguelamer.marvelapp.data.network.MarvelApiConstants.MARVEL_API_TS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiClient {
    @GET("v1/public/characters?&apikey=$MARVEL_API_PUBLIC_KEY&ts=$MARVEL_API_TS&hash=$MARVEL_API_HASH")
    suspend fun getAllCharacters(@Query("offset")offset: Int): Response<MarvelApiResponse>
}