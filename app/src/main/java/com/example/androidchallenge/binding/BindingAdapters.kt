package com.example.androidchallenge.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidchallenge.model.heroes.Thumbnail

@BindingAdapter("imageThumbnail")
fun setImage(view: ImageView, thumbnail: Thumbnail) {
    val url = thumbnail.path + thumbnail.extension
    Glide.with(view.context)
        .load(url).apply(RequestOptions().circleCrop())
        .into(view)
}