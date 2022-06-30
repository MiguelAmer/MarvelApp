package com.miguelamer.marvelapp.domain

import com.miguelamer.marvelapp.data.CharactersRepository
import com.miguelamer.marvelapp.domain.model.MarvelCharacter
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend operator fun invoke(offset: Int): List<MarvelCharacter> {
        return repository.getAllCharacters(offset)
    }
}