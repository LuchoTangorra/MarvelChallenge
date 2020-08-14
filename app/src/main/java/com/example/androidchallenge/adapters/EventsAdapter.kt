package com.example.androidchallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.R
import com.example.androidchallenge.base.BaseAdapter
import com.example.androidchallenge.databinding.EventItemViewBinding
import com.example.androidchallenge.model.Event

class EventsAdapter : BaseAdapter<Event, EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder =
        EventsViewHolder(
            EventItemViewBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.event_item_view,
                    parent,
                    false
                )
            ), onClickListener
        )

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class EventsViewHolder(
    private val binding: EventItemViewBinding,
    private val onClickListener: ((Event) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    private val comicsAdapter = ComicsAdapter()

    fun bind(item: Event) = with(itemView) {
        binding.event = item
        setupAdapter(item)
        binding.root.setOnClickListener {
            onClickListener?.invoke(item)
        }
    }

    private fun setupAdapter(event: Event) {
        binding.comicsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = comicsAdapter
        }
        comicsAdapter.addAll(event.comics.comics)
    }
}