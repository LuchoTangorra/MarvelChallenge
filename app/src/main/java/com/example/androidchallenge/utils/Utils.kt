package com.example.androidchallenge.utils

import android.app.Activity
import android.os.CountDownTimer
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.androidchallenge.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.StringReader
import java.security.MessageDigest

object Utils {

    private var loadingScreen: ViewGroup? = null

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

    fun createLoadingScreen(activity: Activity) {
        removeLoadingScreen(activity)
        val container = LinearLayout(activity)
        container.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        container.setBackgroundColor(activity.getColor(R.color.backgroundLoadingScreen))
        container.gravity = Gravity.CENTER
        container.isClickable = true
        container.isFocusable = true

        val progress = ProgressBar(activity)
        progress.isIndeterminate = true
        container.addView(progress)

        activity.window.decorView.findViewById<ViewGroup>(android.R.id.content).addView(container)
        loadingScreen = container
    }

    fun removeLoadingScreen(activity: Activity) {
        activity.runOnUiThread {
            loadingScreen?.let { loadingScreen ->
                activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
                    .removeView(loadingScreen)
                Utils.loadingScreen = null
            }
        }
    }
}