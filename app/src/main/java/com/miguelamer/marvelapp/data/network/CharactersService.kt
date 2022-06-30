package com.miguelamer.marvelapp.data.network

import com.miguelamer.marvelapp.data.model.MarvelCharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersService @Inject constructor(private val api:CharacterApiClient) {

    suspend fun getCharacters(offset: Int): List<MarvelCharacterModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllCharacters(offset)
            response.body()?.data?.results ?: emptyList()
        }
    }
}