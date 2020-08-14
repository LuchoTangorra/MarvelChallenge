package com.example.androidchallenge.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.example.androidchallenge.R
import com.example.androidchallenge.model.Thumbnail
import com.example.androidchallenge.utils.Utils
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@BindingAdapter("imageThumbnail")
fun setImage(view: ImageView, thumbnail: Thumbnail) {
    val url = "${thumbnail.path}.${thumbnail.extension}".replace("http", "https")
    view.load(url)
}

@BindingAdapter("android:isVisible")
fun View.setIsVisible(visible: Boolean?) {
    visibility = if (visible != null && visible) View.VISIBLE else View.GONE
}

@BindingAdapter("isVisibileIfNotEmpty")
fun TextView.setIsVisible(stringData: String?) {
    visibility = if (!stringData.isNullOrEmpty()) View.VISIBLE else View.GONE
}

@BindingAdapter("setVisibleIfHasElements")
fun <T> View.setVisibleIfHasElements(list: List<T>?) {
    visibility = if (!list.isNullOrEmpty()) View.VISIBLE else View.GONE
}

@BindingAdapter("setVisibleIfHasNoElements")
fun <T> View.setVisibleIfHasNoElements(list: List<T>?) {
    visibility = if (list.isNullOrEmpty()) View.VISIBLE else View.GONE
}

@BindingAdapter("setDate")
fun TextView.setDate(date: String?) {
    date?.let {
        val parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val year = parsedDate.year
        val day = parsedDate.dayOfMonth
        val month = Utils.monthNumberToString(parsedDate.month.value)
        text = "$day de $month $year"
    } ?: run {
        text = resources.getText(R.string.define)
    }
}

@BindingAdapter("modifiyArrowDirection")
fun modifiyArrowDirection(view: ImageView, showing: Boolean) {
    if (showing)
        view.setImageResource(R.drawable.ic_up_arrow_black)
    else
        view.setImageResource(R.drawable.ic_down_arrow_black)
}