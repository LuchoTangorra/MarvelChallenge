package com.example.androidchallenge.heroes.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.dataSource.NetResult
import com.example.androidchallenge.model.heroes.HeroesList
import com.example.androidchallenge.model.heroes.HeroesResponse
import kotlinx.coroutines.launch

class HeroesViewModel(private val marvelRepository: MarvelRepository) : ViewModel() {

    private val _heroes = MutableLiveData<HeroesList>()
    val heroes get() = _heroes

    fun getHeroes(offset: Int) {
        viewModelScope.launch {
            val response = marvelRepository.getHeroes(offset)
            when (response) {
                is NetResult.Success -> {
                    _heroes.postValue(response.data.heroesList)
                }
                is NetResult.Error -> {
                    _heroes.postValue(HeroesList())
                }
            }
        }
    }
}