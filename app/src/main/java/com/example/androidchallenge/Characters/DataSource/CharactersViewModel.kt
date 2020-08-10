package com.example.androidchallenge.Characters.DataSource

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.DataSource.NetResult
import com.example.androidchallenge.Model.Characters.Characters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class CharactersViewModel(private val marvelRepository: MarvelRepository) : ViewModel() {

    private val _characters = MutableLiveData<Characters>()
    val characters get() = _characters

    @ExperimentalCoroutinesApi
    fun getCharacters() {
        viewModelScope.launch {
            when (val response = marvelRepository.getCharacters()) {
                is NetResult.Success -> {
                    _characters.postValue(response.data)
                }
                is NetResult.Error -> {
                }
            }
        }
    }
}