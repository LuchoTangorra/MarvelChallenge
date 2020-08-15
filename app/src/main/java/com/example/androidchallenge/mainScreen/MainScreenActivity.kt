package com.example.androidchallenge.mainScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.androidchallenge.R
import com.example.androidchallenge.binding.setIsVisible
import com.example.androidchallenge.events.EventsFragment
import com.example.androidchallenge.heroes.HeroesFragment
import com.example.androidchallenge.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.lang.Exception

class MainScreenActivity : AppCompatActivity() {

    private val heroesIcons =
        ArrayList<Int>(listOf(R.drawable.ic_superhero, R.drawable.ic_superhero_colored))
    private val eventsIcons =
        ArrayList<Int>(listOf(R.drawable.ic_superhero, R.drawable.ic_superhero_colored))

    private var loadingStateCount: Int = 0
    var loadingStateVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        setupPageAdapter()
    }

    override fun onBackPressed() {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(homeIntent)
        super.onBackPressed()
    }

    fun changeMainScreenLoadingState(state: Boolean) {
        if (state)
            loadingStateCount++
        else
            loadingStateCount--
        loadingStateVisible = loadingStateCount > 0
        this.progressBar.setIsVisible(loadingStateVisible)
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