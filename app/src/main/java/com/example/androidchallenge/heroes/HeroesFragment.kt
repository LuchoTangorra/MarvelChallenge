package com.example.androidchallenge.heroes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.R
import com.example.androidchallenge.adapters.HeroesAdapter
import com.example.androidchallenge.heroes.dataSource.HeroesViewModel
import com.example.androidchallenge.heroDetails.HeroDetailsActivity
import com.example.androidchallenge.mainScreen.MainScreenActivity
import com.example.androidchallenge.utils.Constants
import com.example.androidchallenge.utils.Utils
import com.example.androidchallenge.utils.decorators.MarginItemDecoration
import kotlinx.android.synthetic.main.fragment_heroes.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception


class HeroesFragment : Fragment() {

    private val viewModel: HeroesViewModel by viewModel()
    private val heroesAdapter = HeroesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_heroes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        setupListener()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupListener() {
        heroesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    getHeroes(heroesAdapter.list.size)
                }
            }
        })
    }

    private fun setupRecycler() {
        heroesRecyclerView.apply {
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
        viewModel.heroes.observe(viewLifecycleOwner, Observer {
            it.heroes.forEach { hero ->
                //We need to refresh only the new elements, not the whole list
                heroesAdapter.add(hero)
            }
            changeLoadingState(false)
            //Utils.removeLoadingScreen(requireActivity())
        })

        getHeroes()
    }

    private fun getHeroes(offset: Int = 0) {
        if (!getLoadingState()) {
            heroesRecyclerView.post {
                viewModel.getHeroes(offset)
            }
            changeLoadingState(true)
        }
        //Utils.createLoadingScreen(requireActivity())
    }

    private fun changeLoadingState(loading: Boolean) {
        try {
            (activity as MainScreenActivity).changeMainScreenLoadingState(loading)
        } catch (e: Exception) {
        }
    }

    private fun getLoadingState(): Boolean {
        var visible = false
        try {
            visible = (activity as MainScreenActivity).loadingStateVisible
        } catch (e: Exception) {
        }
        return visible
    }

}