package com.example.androidchallenge.utils

import android.app.Activity
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.marginTop
import com.example.androidchallenge.R
import com.example.androidchallenge.binding.setIsVisible
import com.example.androidchallenge.mainScreen.MainScreenActivity
import com.example.androidchallenge.model.ComicDate
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.io.StringReader
import java.lang.Exception
import java.security.MessageDigest

object Utils {

    private var loadingScreen: ViewGroup? = null

    fun generateHash(): String = MessageDigest.getInstance("MD5")
        .digest(("1${Constants.marvelPrivateAPIkey}${Constants.marvelPublicAPIkey}").toByteArray())
        .joinToString("") { "%02x".format(it) }

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

    fun monthNumberToString(month: Int): String {
        return when (month) {
            1 -> "Enero"
            2 -> "Febrero"
            3 -> "Marzo"
            4 -> "Abril"
            5 -> "Mayo"
            6 -> "Junio"
            7 -> "Julio"
            8 -> "Agosto"
            9 -> "Septiembre"
            10 -> "Octubre"
            11 -> "Noviembre"
            12 -> "Diciembre"
            else -> ""
        }
    }

    fun getYear(dates: List<ComicDate>): String {
        dates.forEach {
            if (it.type == Constants.comicsDateUsage) {
                try {
                    return (it.date.subSequence(0, 4).toString().toInt()).toString()
                } catch (e: Exception) {
                }
            }
        }
        return "0"
    }
}