package com.example.androidchallenge.events.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.dataSource.NetResult
import com.example.androidchallenge.heroes.dataSource.MarvelRepository
import com.example.androidchallenge.model.ComicsList
import com.example.androidchallenge.model.Event
import com.example.androidchallenge.model.EventsList
import com.example.androidchallenge.utils.Utils
import kotlinx.coroutines.launch
import java.util.*

class EventsViewModel(private val marvelRepository: MarvelRepository) : ViewModel() {

    var eventWithVisibleComics: Event? = null

    private val _events = MutableLiveData<EventsList>()
    val events get() = _events

    fun getEvents() {
        viewModelScope.launch {
            when (val response = marvelRepository.getEvents()) {
                is NetResult.Success -> {
                    _events.postValue(response.data.eventsList)
                    response.data.eventsList.events.forEach { event ->
                        getEventComics(event.id)
                    }
                }
                is NetResult.Error -> {
                    _events.postValue(EventsList())
                }
            }
        }
    }

    private fun getEventComics(eventId: Int) {
        viewModelScope.launch {
            when (val response = marvelRepository.getEventComics(eventId)) {
                is NetResult.Success -> {
                    events.value?.events?.let {
                        val event = getEvent(it, eventId)
                        event.comics = response.data.comicsList
                        event.comics.comics.map { it.year = Utils.getYear(it.dates) }

                        event.comics.comics = event.comics.comics.filter { it.year.toInt() > 1900 }
                    }
                }
                is NetResult.Error -> {
                }
            }
        }
    }

    private fun getEvent(events: List<Event>, eventId: Int): Event {
        events.forEach { event ->
            if (event.id == eventId) {
                return event
            }
        }
        return Event()
    }
}