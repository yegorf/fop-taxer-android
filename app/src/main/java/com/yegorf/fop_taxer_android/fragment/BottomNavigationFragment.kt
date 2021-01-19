package com.yegorf.fop_taxer_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.yegorf.fop_taxer_android.FragmentNavigator
import com.yegorf.fop_taxer_android.R
import com.yegorf.fop_taxer_android.databinding.FragmentBottomNavigationBinding

class BottomNavigationFragment : Fragment() {

    lateinit var selectedTab: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBottomNavigationBinding.inflate(inflater)
        setOnClickListeners(binding)
        return binding.root
    }

    private fun setOnClickListeners(binding: FragmentBottomNavigationBinding) {
        selectedTab = binding.tabCalculation;
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
        if (activity != null) {
            selectedTab.drawable.setTint(activity!!.resources.getColor(R.color.lightGrey))
            newSelectedTab.drawable.setTint(activity!!.resources.getColor(R.color.black))
            selectedTab = newSelectedTab
        }
    }
}