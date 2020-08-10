package com.example.androidchallenge.MainScreen

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.androidchallenge.Characters.CharactersFragment
import com.example.androidchallenge.Events.EventsFragment
import com.example.androidchallenge.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_characters.*

class MainScreenActivity : AppCompatActivity() {

    val charactersIcons = ArrayList<Int>(listOf(R.drawable.ic_superhero, R.drawable.ic_superhero_colored))
    val eventsIcons = ArrayList<Int>(listOf(R.drawable.ic_superhero, R.drawable.ic_superhero_colored))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        setupToolbar()
        setupPageAdapter()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.background = ColorDrawable(ContextCompat.getColor(this, R.color.toolbar));
    }

    private fun setupPageAdapter() {
        mainScreenViewPager.adapter = ScreenSlidePageAdapter(this)
        TabLayoutMediator(pageIndicator, mainScreenViewPager) { _, _ -> }.attach()
        setupPageIndicatorIcons()
        setTabLayoutTitle()

        mainScreenViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        pageIndicator.getTabAt(0)?.setIcon(charactersIcons[1])
                        pageIndicator.getTabAt(1)?.setIcon(eventsIcons[0])
                    }
                    else -> {
                        pageIndicator.getTabAt(0)?.setIcon(charactersIcons[0])
                        pageIndicator.getTabAt(1)?.setIcon(eventsIcons[1])
                    }
                }

            }
        })
    }

    private fun setTabLayoutTitle() {
        pageIndicator.getTabAt(0)?.text = getText(R.string.charactersTitle)
        pageIndicator.getTabAt(1)?.text = getText(R.string.eventsTitle)
    }

    private fun setupPageIndicatorIcons() {
        val imageResId = ArrayList<Int>(listOf(charactersIcons[0], eventsIcons[0]))
        imageResId.forEachIndexed { i, resId ->
            pageIndicator.getTabAt(i)?.setIcon(resId)
        }
    }

    private inner class ScreenSlidePageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CharactersFragment()
                else -> EventsFragment()
            }
        }
    }
}