package com.miguelamer.marvelapp.data

import com.miguelamer.marvelapp.data.model.MarvelCharacterModel
import com.miguelamer.marvelapp.data.network.CharactersService
import com.miguelamer.marvelapp.domain.model.MarvelCharacter
import com.miguelamer.marvelapp.domain.model.toDomain
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val api: CharactersService
) {

    suspend fun getAllCharacters(offset: Int) : List<MarvelCharacter> {
        val response: List<MarvelCharacterModel> = api.getCharacters(offset)
        return response.map { it.toDomain()}
    }
}