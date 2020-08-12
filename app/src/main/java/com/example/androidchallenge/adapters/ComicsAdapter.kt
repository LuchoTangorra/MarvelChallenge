package com.example.androidchallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.R
import com.example.androidchallenge.base.BaseAdapter
import com.example.androidchallenge.databinding.ComicItemViewBinding
import com.example.androidchallenge.model.heroes.Appearance

class ComicsAdapter : BaseAdapter<Appearance, ComicsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder =
        ComicsViewHolder(
            ComicItemViewBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.comic_item_view,
                    parent,
                    false
                )
            ), onClickListener
        )

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class ComicsViewHolder(
    private val binding: ComicItemViewBinding,
    private val onClickListener: ((Appearance) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Appearance) = with(itemView) {
        binding.comic = item
        binding.root.setOnClickListener {
            onClickListener?.invoke(item)
        }
    }
}