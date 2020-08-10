package com.example.androidchallenge.Model.Characters

data class Result(
    val comics: Appearences = Appearences(),
    val description: String = "",
    val events: Appearences = Appearences(),
    val id: Int = 0,
    val name: String = "",
    val series: Appearences = Appearences(),
    val stories: Appearences = Appearences(),
    val thumbnail: Thumbnail = Thumbnail(),
    val urls: List<Url> = listOf()
)