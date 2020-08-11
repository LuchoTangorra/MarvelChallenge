package com.example.androidchallenge.Heroes.DataSource

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.DataSource.NetResult
import com.example.androidchallenge.Model.Heroes.Heroes
import kotlinx.coroutines.launch

class HeroesViewModel(private val marvelRepository: MarvelRepository) : ViewModel() {

    private val _heroes = MutableLiveData<Heroes>()
    val heroes get() = _heroes

    fun getHeroes() {
        viewModelScope.launch {
            when (val response = marvelRepository.getHeroes()) {
                is NetResult.Success -> {
                    _heroes.postValue(response.data)
                }
                is NetResult.Error -> {
                }
            }
        }
    }
}