package com.example.androidchallenge.Heroes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.Adapters.HeroesAdapter
import com.example.androidchallenge.Heroes.DataSource.HeroesViewModel
import com.example.androidchallenge.Model.Heroes.Hero
import com.example.androidchallenge.R
import com.example.androidchallenge.databinding.FragmentHeroesBinding
import kotlinx.android.synthetic.main.fragment_heroes.*
import org.koin.android.viewmodel.ext.android.viewModel

class HeroesFragment : Fragment() {

    private val heroes = ArrayList<Hero>()

    private val viewModel: HeroesViewModel by viewModel()
    private lateinit var binding: FragmentHeroesBinding
    private val heroesAdapter = HeroesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_heroes, container, false)
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
            heroes.clear()
            heroes.addAll(it.data.heroes)
            heroesAdapter.addAll(heroes)
        })
    }

    private fun setupHeroessRecycler() {
        heroesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        heroesRecyclerView.adapter = heroesAdapter
        heroesAdapter.addAll(heroes)
    }
}