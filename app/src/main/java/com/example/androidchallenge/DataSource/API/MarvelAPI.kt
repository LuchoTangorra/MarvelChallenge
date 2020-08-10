package com.example.androidchallenge.DataSource.API

import com.example.androidchallenge.Model.Characters.Characters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MarvelAPI {
    @GET("characters")
    suspend fun getCharacters(@Header("apikey") apikey: String,
                              @Query("limit") limit: Int): Response<Characters>
}