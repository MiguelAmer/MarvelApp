package com.miguelamer.marvelapp.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelamer.marvelapp.domain.GetCharactersUseCase
import com.miguelamer.marvelapp.domain.model.MarvelCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
): ViewModel() {

    val characterModelList = MutableLiveData<List<MarvelCharacter>>()
    val isLoading = MutableLiveData<Boolean>()

    fun loadCharacters(offset: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val response = getCharactersUseCase(offset)

            if (!response.isNullOrEmpty()) {
                characterModelList.postValue(response)
                isLoading.postValue(false)
            }
        }
    }
}