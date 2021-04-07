package com.yegorf.fop_taxer_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.yegorf.fop_taxer_android.PreferencesManager
import com.yegorf.fop_taxer_android.databinding.FragmentReminderBinding

class ReminderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReminderBinding.inflate(inflater)
        initViews(binding)
        return binding.root
    }

    private fun initViews(binding: FragmentReminderBinding) {
        activity?.let {
            val manager = PreferencesManager(it)
            binding.switchNotifications.isChecked = manager.isShowNotifications()
            binding.switchNotifications.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
                manager.setNotificationsOn(b)
            }
        }
    }
}