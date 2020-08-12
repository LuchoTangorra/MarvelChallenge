package com.example.androidchallenge.model.heroes

import com.google.gson.annotations.SerializedName

data class Appearances(
    val available: Int = 0,
    val collectionURI: String = "",
    @SerializedName("items")
    val appearances: List<Appearance> = listOf(),
    val returned: Int = 0
)