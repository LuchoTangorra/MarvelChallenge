package com.example.androidchallenge.heroDetails.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.dataSource.NetResult
import com.example.androidchallenge.heroes.dataSource.MarvelRepository
import com.example.androidchallenge.model.comics.ComicsList
import com.example.androidchallenge.model.comics.ComicsResponse
import com.example.androidchallenge.model.heroes.Hero
import kotlinx.coroutines.launch

class ComicsViewModel(private val marvelRepository: MarvelRepository): ViewModel() {

    lateinit var hero: Hero

    private val _comics = MutableLiveData<ComicsList>()
    val comics get() = _comics

    fun getComics(characterId: Int) {
        viewModelScope.launch {
            when (val response = marvelRepository.getComics(characterId)) {
                is NetResult.Success -> {
                    _comics.postValue(response.data.comicsList)
                }
                is NetResult.Error -> {
                    _comics.postValue(ComicsList())
                }
            }
        }
    }
}