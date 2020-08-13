package com.example.androidchallenge.model.heroes

import com.google.gson.annotations.SerializedName

data class HeroesResponse(
    val code: Int = 0,
    @SerializedName("data")
    val heroesList: HeroesList = HeroesList()
)

data class HeroesList(
    @SerializedName("results")
    val heroes: List<Hero> = listOf()
)

data class Hero(
    val comics: Appearances = Appearances(),
    val description: String = "",
    val events: Appearances = Appearances(),
    val id: Int = 0,
    val name: String = "",
    val series: Appearances = Appearances(),
    val stories: Appearances = Appearances(),
    val thumbnail: Thumbnail = Thumbnail(),
    val urls: List<Url> = listOf()
)

data class Appearances(
    val available: Int = 0,
    val collectionURI: String = "",
    @SerializedName("items")
    val appearances: List<Appearance> = listOf(),
    val returned: Int = 0
)

data class Appearance(
    val name: String = "",
    val resourceURI: String = "",
    val type: String = ""
)

data class Thumbnail(
    val extension: String = "",
    val path: String = ""
)

data class Url(
    val type: String = "",
    val url: String = ""
)