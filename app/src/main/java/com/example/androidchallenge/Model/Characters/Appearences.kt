package com.example.androidchallenge.Model.Characters

data class Appearences(
    val available: Int = 0,
    val collectionURI: String = "",
    val items: List<Item> = listOf(),
    val returned: Int = 0
)