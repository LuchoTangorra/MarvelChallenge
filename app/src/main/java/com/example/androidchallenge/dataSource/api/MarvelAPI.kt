package com.example.androidchallenge.dataSource.api

import com.example.androidchallenge.model.ComicsResponse
import com.example.androidchallenge.model.EventsResponse
import com.example.androidchallenge.model.HeroesResponse
import retrofit2.Response
import retrofit2.http.*

interface MarvelAPI {
    @GET("characters")
    suspend fun getHeroes(
        @QueryMap auth: HashMap<String, String>,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<HeroesResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: Int,
        @QueryMap auth: HashMap<String, String>,
        @Query("orderBy") orderBy: String
    ): Response<ComicsResponse>

    @GET("events/{eventId}/comics")
    suspend fun getEventComics(
        @Path("eventId") eventId: Int,
        @QueryMap auth: HashMap<String, String>,
        @Query("orderBy") orderBy: String
    ): Response<ComicsResponse>

    @GET("events")
    suspend fun getEvents(
        @QueryMap auth: HashMap<String, String>,
        @Query("limit") limit: Int,
        @Query("orderBy") orderBy: String
    ): Response<EventsResponse>
}