package com.example.androidchallenge.heroes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.adapters.HeroesAdapter
import com.example.androidchallenge.heroes.dataSource.HeroesViewModel
import com.example.androidchallenge.R
import com.example.androidchallenge.databinding.FragmentHeroesBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HeroesFragment : Fragment() {

    private val viewModel: HeroesViewModel by viewModel()
    private lateinit var binding: FragmentHeroesBinding
    private val heroesAdapter = HeroesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_heroes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupHeroessRecycler()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getHeroes()
        viewModel.heroes.observe(requireActivity(), Observer {
            heroesAdapter.update(it.data.heroes)
        })
    }

    private fun setupHeroessRecycler() {
        binding.heroesRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = heroesAdapter
        }
    }
}