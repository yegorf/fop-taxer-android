package com.yegorf.fop_taxer_android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.yegorf.fop_taxer_android.fragment.CalculationFragment
import com.yegorf.fop_taxer_android.fragment.CalendarFragment
import com.yegorf.fop_taxer_android.fragment.ReminderFragment

class FragmentNavigator(private val fragmentManager: FragmentManager) {

    private val homeScreen = Screen.CALENDAR

    companion object {
        private lateinit var currentScreen: Screen
    }

    enum class Screen {
        CALCULATION,
        CALENDAR,
        REMINDER
    }

    private fun getFragmentForScreen(screen: Screen): Fragment {
        return when (screen) {
            Screen.CALCULATION -> CalculationFragment()
            Screen.CALENDAR -> CalendarFragment()
            Screen.REMINDER -> ReminderFragment()
        }
    }

    fun openFragment(screen: Screen) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, getFragmentForScreen(screen))
            .addToBackStack(screen.name)
            .commit()
        currentScreen = screen
    }

    fun navigateBack(activity: FragmentActivity) {
        if (currentScreen == homeScreen) {
            activity.finish()
        } else {
            openFragment(homeScreen)
        }
    }
}