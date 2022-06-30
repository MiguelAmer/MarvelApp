package com.miguelamer.marvelapp.domain.model

import com.miguelamer.marvelapp.data.model.MarvelCharacterModel
import com.miguelamer.marvelapp.data.model.Image
import java.io.Serializable

data class MarvelCharacter(val id: Int, val name: String, val description: String, val thumbnail: Image): Serializable

fun MarvelCharacterModel.toDomain() = MarvelCharacter(id, name, description, thumbnail)