package com.example.androidchallenge.heroes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.R
import com.example.androidchallenge.adapters.HeroesAdapter
import com.example.androidchallenge.databinding.FragmentHeroesBinding
import com.example.androidchallenge.heroes.dataSource.HeroesViewModel
import com.example.androidchallenge.heroDetails.HeroDetailsActivity
import com.example.androidchallenge.utils.Constants
import com.example.androidchallenge.utils.Constants.heroesOffset
import com.example.androidchallenge.utils.Utils
import com.example.androidchallenge.utils.decorators.MarginItemDecoration
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

        setupRecycler()
        setupListener()
    }

    private fun setupListener() {
        binding.heroesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    setupHeroes(heroesOffset)
                }
            }
        })
    }

    private fun setupRecycler() {
        binding.heroesRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.recyclerMargin).toInt()
                )
            )

            adapter = heroesAdapter
        }
        heroesAdapter.onClickListener = {
            val i = Intent(requireContext(), HeroDetailsActivity::class.java)
            i.putExtra(Constants.hero, Utils.serializeToJson(it))
            startActivity(i)
        }
        setupHeroes()
    }

    private fun setupHeroes(offset: Int = 0) {
        viewModel.getHeroes(offset)
        Utils.createLoadingScreen(requireActivity())
        viewModel.heroes.observe(requireActivity(), Observer {
            val filteredHeroes = it.heroes.filter { !viewModel.heroesId.contains(it.id) }
            if (filteredHeroes.isNotEmpty()) {
                heroesAdapter.addAll(filteredHeroes)
                filteredHeroes.forEach {
                    viewModel.heroesId.add(it.id)
                }
            }
            Utils.removeLoadingScreen(requireActivity())
        })
    }
}