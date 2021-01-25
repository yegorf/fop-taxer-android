package com.yegorf.fop_taxer_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yegorf.fop_taxer_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
        FragmentNavigator(supportFragmentManager).openFragment(FragmentNavigator.Screen.CALENDAR)
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            val screen = when (it.itemId) {
                R.id.navigation_calendar -> FragmentNavigator.Screen.CALENDAR
                R.id.navigation_calculation -> FragmentNavigator.Screen.CALCULATION
                R.id.navigation_reminder -> FragmentNavigator.Screen.REMINDER
                else -> FragmentNavigator.Screen.CALENDAR
            }
            FragmentNavigator(supportFragmentManager).openFragment(screen)
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        if (FragmentNavigator.currentScreen == FragmentNavigator.homeScreen) {
            finish()
        } else {
            binding.bottomNavigation.selectedItemId = R.id.navigation_calendar
        }
    }
}