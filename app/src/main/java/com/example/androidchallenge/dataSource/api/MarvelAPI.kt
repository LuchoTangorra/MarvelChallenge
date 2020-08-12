package com.example.androidchallenge.dataSource.api

import com.example.androidchallenge.heroes.dataSource.AuthParams
import com.example.androidchallenge.model.heroes.Heroes
import retrofit2.Response
import retrofit2.http.*

interface MarvelAPI {
    @GET("characters")
    suspend fun getHeroes(@QueryMap auth: HashMap<String, String>,
                          @Query("limit") limit: Int): Response<Heroes>
}