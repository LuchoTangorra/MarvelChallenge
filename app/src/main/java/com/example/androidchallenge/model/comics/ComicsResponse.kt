package com.example.androidchallenge.model.comics

import com.google.gson.annotations.SerializedName
import java.time.Year
import java.util.*

data class ComicsResponse(
    val code: Int = 0,
    @SerializedName("data")
    val comicsList: ComicsList = ComicsList()
)

data class ComicsList(
    @SerializedName("results")
    val comics: List<Comic> = listOf()
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