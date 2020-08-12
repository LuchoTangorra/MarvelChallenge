package com.example.androidchallenge.mainScreen

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.androidchallenge.heroes.HeroesFragment
import com.example.androidchallenge.events.EventsFragment
import com.example.androidchallenge.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.common_toolbar.view.*

class MainScreenActivity : AppCompatActivity() {

    val heroesIcons = ArrayList<Int>(listOf(R.drawable.ic_superhero, R.drawable.ic_superhero_colored))
    val eventsIcons = ArrayList<Int>(listOf(R.drawable.ic_superhero, R.drawable.ic_superhero_colored))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        setupPageAdapter()
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
                        pageIndicator.getTabAt(0)?.setIcon(heroesIcons[1])
                        pageIndicator.getTabAt(1)?.setIcon(eventsIcons[0])
                    }
                    else -> {
                        pageIndicator.getTabAt(0)?.setIcon(heroesIcons[0])
                        pageIndicator.getTabAt(1)?.setIcon(eventsIcons[1])
                    }
                }

            }
        })
    }

    private fun setTabLayoutTitle() {
        pageIndicator.getTabAt(0)?.text = getText(R.string.heroesTitle)
        pageIndicator.getTabAt(1)?.text = getText(R.string.eventsTitle)
    }

    private fun setupPageIndicatorIcons() {
        val imageResId = ArrayList<Int>(listOf(heroesIcons[0], eventsIcons[0]))
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
                0 -> HeroesFragment()
                else -> EventsFragment()
            }
        }
    }
}