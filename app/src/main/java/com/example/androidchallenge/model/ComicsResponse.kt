package com.example.androidchallenge.model

import com.google.gson.annotations.SerializedName

data class ComicsResponse(
    val code: Int = 0,
    @SerializedName("data")
    val comicsList: ComicsList = ComicsList()
)

data class ComicsList(
    @SerializedName("results")
    var comics: List<Comic> = listOf()
)

data class Comic(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val dates: List<ComicDate> = listOf(),
    var year: String = ""
)

data class ComicDate(
    val type: String = "",
    val date: String= ""
)