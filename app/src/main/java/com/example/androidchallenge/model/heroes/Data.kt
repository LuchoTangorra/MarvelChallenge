package com.example.androidchallenge.model.heroes

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("results")
    val heroes: List<Hero> = listOf()
)