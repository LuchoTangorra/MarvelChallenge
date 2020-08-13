package com.example.androidchallenge.heroDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.adapters.ComicsAdapter
import com.example.androidchallenge.binding.setIsVisible
import com.example.androidchallenge.databinding.ActivityHeroDetailsBinding
import com.example.androidchallenge.heroDetails.dataSource.ComicsViewModel
import com.example.androidchallenge.model.comics.Comic
import com.example.androidchallenge.model.comics.ComicDate
import com.example.androidchallenge.model.heroes.Hero
import com.example.androidchallenge.utils.Constants
import com.example.androidchallenge.utils.Utils
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.common_toolbar.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class HeroDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroDetailsBinding
    private val comicsAdapter = ComicsAdapter()
    private val viewModel: ComicsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.hero = getHero()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupToolbar()
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.comicsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@HeroDetailsActivity, RecyclerView.VERTICAL, false)

            adapter = comicsAdapter
        }

        viewModel.comics.observe(this, Observer {
            var comicList: List<Comic> = it.comics
            comicList.map { it.year = getDate(it.dates) }
            comicList.filter { it.year != "" }
            comicList = comicList.sortedBy { it.year.toInt() }

            comicsAdapter.update(comicList)
        })
        viewModel.getComics(viewModel.hero.id)
    }

    private fun getDate(dates: List<ComicDate>): String {
        dates.forEach {
            if (it.type == "onsaleDate") {
                try {
                    return (it.date.subSequence(0, 4).toString().toInt()).toString()
                } catch (e: Exception) {
                }
            }
        }
        return ""
    }

    private fun setupToolbar() {
        binding.includedToolbar.toolbar.exitButton.setIsVisible(true)
        titleTextView.text = viewModel.hero.name
        binding.includedToolbar.toolbar.exitButton.setOnClickListener { onBackPressed() }
    }

    private fun getHero(): Hero =
        intent.extras?.get(Constants.hero)?.let { heroString ->
            Utils.fromJsonString<Hero>(heroString as String)
        } ?: run { Hero() }


    override fun onDestroy() {
        super.onDestroy()
        binding.includedToolbar.toolbar.exitButton.setIsVisible(false)
    }
}