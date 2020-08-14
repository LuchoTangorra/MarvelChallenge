package com.example.androidchallenge.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.R
import com.example.androidchallenge.adapters.EventsAdapter
import com.example.androidchallenge.databinding.FragmentEventsBinding
import com.example.androidchallenge.events.dataSource.EventsViewModel
import com.example.androidchallenge.model.Event
import com.example.androidchallenge.utils.Utils
import com.example.androidchallenge.utils.decorators.MarginItemDecoration
import org.koin.android.viewmodel.ext.android.viewModel

class EventsFragment : Fragment() {

    private val viewModel: EventsViewModel by viewModel()
    private lateinit var binding: FragmentEventsBinding
    private val eventsAdapter = EventsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
    }

    private fun setupRecycler() {
        binding.eventsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.recyclerMargin).toInt()
                )
            )

            adapter = eventsAdapter
        }
        eventsAdapter.onClickListener = { clickedEvent ->
            eventsAdapter.updatedItems(changeExpandedEvent(clickedEvent))
            var scrollToPositionEnabled = true
            eventsAdapter.postFillRecycler = { showingComicsEvent ->
                if (scrollToPositionEnabled) {
                    scrollToPositionEnabled = false
                    binding.eventsRecyclerView.scrollToPosition(
                        eventsAdapter.list.indexOf(
                            showingComicsEvent
                        )
                    )
                }
            }
        }

        viewModel.events.observe(viewLifecycleOwner, Observer {
            eventsAdapter.update(it.events)
            Utils.removeLoadingScreen(requireActivity())
        })

        getEvents()
    }

    private fun changeExpandedEvent(event: Event): List<Event> {
        val events = arrayListOf(event)
        viewModel.eventWithVisibleComics?.let {
            events.add(it)
        }
        if (!event.showingComics) {
            viewModel.eventWithVisibleComics?.showingComics = false
            viewModel.eventWithVisibleComics = event
        } else {
            viewModel.eventWithVisibleComics = null
        }
        event.showingComics = !event.showingComics
        return events
    }

    private fun getEvents() {
        viewModel.getEvents()
        Utils.createLoadingScreen(requireActivity())
    }
}