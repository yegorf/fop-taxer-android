package com.yegorf.fop_taxer_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.yegorf.fop_taxer_android.FragmentNavigator
import com.yegorf.fop_taxer_android.R
import com.yegorf.fop_taxer_android.databinding.FragmentBottomNavigationBinding

class BottomNavigationFragment : Fragment() {

    lateinit var selectedTab: ImageView
    private val animationDuration = 120L
    private val scalingKoef = 1.2f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBottomNavigationBinding.inflate(inflater)

        selectedTab = binding.tabCalendar
        animateSelectTab(selectedTab)

        setOnClickListeners(binding)
        return binding.root
    }

    private fun setOnClickListeners(binding: FragmentBottomNavigationBinding) {
        if (fragmentManager != null) {
            val fragmentNavigator = FragmentNavigator(fragmentManager!!)
            binding.tabCalculation.setOnClickListener {
                fragmentNavigator.openFragment(FragmentNavigator.Screen.CALCULATION)
                selectTab(it as ImageView)
            }
            binding.tabCalendar.setOnClickListener {
                fragmentNavigator.openFragment(FragmentNavigator.Screen.CALENDAR)
                selectTab(it as ImageView)
            }
            binding.tabReminder.setOnClickListener {
                fragmentNavigator.openFragment(FragmentNavigator.Screen.REMINDER)
                selectTab(it as ImageView)
            }
        }
    }

    private fun selectTab(newSelectedTab: ImageView) {
        if (activity != null && newSelectedTab != selectedTab) {
            animateSelectTab(newSelectedTab)
            animateUnselectTab(selectedTab)
            selectedTab = newSelectedTab
        }
    }

    private fun animateSelectTab(tab: ImageView) {
        val selectAnimation = ScaleAnimation(
            1f, scalingKoef, 1f, scalingKoef,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        selectAnimation.duration = animationDuration
        selectAnimation.fillAfter = true
        tab.startAnimation(selectAnimation)
        tab.drawable.setTint(activity!!.resources.getColor(R.color.black))
    }

    private fun animateUnselectTab(tab: ImageView) {
        val unselectAnimation = ScaleAnimation(
            scalingKoef, 1f, scalingKoef, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        unselectAnimation.duration = animationDuration
        unselectAnimation.fillAfter = true
        tab.startAnimation(unselectAnimation)
        tab.drawable.setTint(activity!!.resources.getColor(R.color.lightGrey))
    }
}