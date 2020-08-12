package com.example.androidchallenge.utils

import java.security.MessageDigest

object Utils {
    fun generateHash(): String = MessageDigest.getInstance("MD5")
        .digest(("1${Constants.marvelPrivateAPIkey}${Constants.marvelPublicAPIkey}").toByteArray())
        .joinToString("") { "%02x".format(it) }
}