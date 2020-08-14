package com.example.androidchallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.R
import com.example.androidchallenge.base.BaseAdapter
import com.example.androidchallenge.databinding.EventItemViewBinding
import com.example.androidchallenge.model.Event

class EventsAdapter : BaseAdapter<Event, EventsViewHolder>() {

    var postFillRecycler: ((Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder =
        EventsViewHolder(
            EventItemViewBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.event_item_view,
                    parent,
                    false
                )
            ), onClickListener,
            postFillRecycler
        )

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class EventsViewHolder(
    private val binding: EventItemViewBinding,
    private val onClickListener: ((Event) -> Unit)?,
    private val postFillRecycler: ((Event) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    private val comicsAdapter = ComicsAdapter()
    private var setAdapter: Boolean = true

    fun bind(item: Event) = with(itemView) {
        binding.event = item
        if (setAdapter)
            setupAdapter()

        comicsAdapter.update(item.comics.comics)

        binding.comicsRecyclerView.post {
            if (item.showingComics)
                postFillRecycler?.invoke(item)
        }

        binding.root.setOnClickListener {
            onClickListener?.invoke(item)
        }
    }

    private fun setupAdapter() {
        binding.comicsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = comicsAdapter
        }
    }
}