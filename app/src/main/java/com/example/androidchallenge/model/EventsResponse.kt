package com.example.androidchallenge.model

import com.google.gson.annotations.SerializedName

data class EventsResponse(
    val code: Int = 0,
    @SerializedName("data")
    val eventsList: EventsList = EventsList()
)

data class EventsList(
    @SerializedName("results")
    val events: List<Event> = listOf()
)

data class Event(
    val id: Int = 0,
    val title: String = "",
    val start: String = "",
    val end: String = "",
    val thumbnail: Thumbnail = Thumbnail(),
    var comics: ComicsList = ComicsList(),
    var showingComics: Boolean = false
)