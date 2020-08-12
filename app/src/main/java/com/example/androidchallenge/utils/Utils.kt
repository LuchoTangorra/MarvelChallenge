package com.example.androidchallenge.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.StringReader
import java.security.MessageDigest

object Utils {
    fun generateHash(): String = MessageDigest.getInstance("MD5")
        .digest(("1${Constants.marvelPrivateAPIkey}${Constants.marvelPublicAPIkey}").toByteArray())
        .joinToString("") { "%02x".format(it) }

    //GSON serialize - deserialize
    fun <T> serializeToJson(_class: T): String {
        val gson = GsonBuilder().disableHtmlEscaping().create()
        return gson.toJson(_class)
    }

    inline fun <reified T> fromJsonString(jsonString: String, isList: Boolean = false): T {
        val stringReader = StringReader(jsonString)

        return Gson().fromJson(stringReader, object : TypeToken<T>() {}.type)
    }
}