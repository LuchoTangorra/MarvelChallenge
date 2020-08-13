package com.example.androidchallenge.dataSource.api

import com.example.androidchallenge.model.comics.ComicsResponse
import com.example.androidchallenge.model.heroes.HeroesResponse
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
        @QueryMap auth: HashMap<String, String>
    ): Response<ComicsResponse>
}