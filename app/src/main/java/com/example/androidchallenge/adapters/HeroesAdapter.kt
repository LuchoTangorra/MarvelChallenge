package com.example.androidchallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.base.BaseAdapter
import com.example.androidchallenge.model.heroes.Hero
import com.example.androidchallenge.R
import com.example.androidchallenge.databinding.HeroItemViewBinding

class HeroesAdapter : BaseAdapter<Hero, HeroesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder =
        HeroesViewHolder(
            HeroItemViewBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.hero_item_view,
                    parent,
                    false
                )
            ), onClickListener
        )

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class HeroesViewHolder(
    private val binding: HeroItemViewBinding,
    private val onClickListener: ((Hero) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Hero) = with(itemView) {
        binding.hero = item
        binding.root.setOnClickListener {
            onClickListener?.invoke(item)
        }
    }
}