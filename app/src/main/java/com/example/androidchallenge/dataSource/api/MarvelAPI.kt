package com.example.androidchallenge.dataSource.api

import com.example.androidchallenge.model.heroes.Heroes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MarvelAPI {
    @GET("characters")
    suspend fun getHeroes(@Query("apikey") apikey: String,
                          @Query("limit") limit: Int): Response<Heroes>
}