package com.example.androidchallenge.heroDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.adapters.ComicsAdapter
import com.example.androidchallenge.binding.setIsVisible
import com.example.androidchallenge.databinding.ActivityHeroDetailsBinding
import com.example.androidchallenge.model.heroes.Hero
import com.example.androidchallenge.utils.Constants
import com.example.androidchallenge.utils.Utils
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.common_toolbar.view.*

class HeroDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroDetailsBinding
    private lateinit var hero: Hero
    private val comicsAdapter = ComicsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hero = getHero()
        binding.hero = hero
        binding.heroDescription.setIsVisible(hero.description.isNotEmpty())

        setupToolbar()
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.comicsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@HeroDetailsActivity, RecyclerView.VERTICAL, false)

            adapter = comicsAdapter
        }

        comicsAdapter.update(hero.comics.appearances)
    }

    private fun setupToolbar() {
        binding.includedToolbar.toolbar.exitButton.setIsVisible(true)
        titleTextView.text = hero.name
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