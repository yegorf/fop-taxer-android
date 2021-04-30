package com.yegorf.fop_taxer_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.yegorf.fop_taxer_android.PreferencesManager
import com.yegorf.fop_taxer_android.R
import com.yegorf.fop_taxer_android.databinding.FragmentSettingsBinding
import com.yegorf.fop_taxer_android.tools.EmailManager

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        initViews()
        return binding.root
    }

    private fun initViews() {
        val context = context
        context?.let {
            val preferencesManager = PreferencesManager(context)
            val taxGroup = preferencesManager.getTaxGroup()
            binding.radioGroup1.isChecked = taxGroup == 1
            binding.radioGroup2.isChecked = taxGroup == 2
            binding.radioGroup3.isChecked = taxGroup == 3

            binding.rgTaxGroup.setOnCheckedChangeListener { _, checkedId ->
                val selectedVariant = when (checkedId) {
                    R.id.radio_group_1 -> 1
                    R.id.radio_group_2 -> 2
                    R.id.radio_group_3 -> 3
                    else -> 3
                }
                preferencesManager.setTaxGroup(selectedVariant)
            }

            binding.switchNotificationsSound.isChecked = preferencesManager.isNotificationsSoundOn()
            binding.switchNotificationsSound.setOnCheckedChangeListener { _, isChecked ->
                preferencesManager.setNotificationsSoundOn(isChecked)
            }

            binding.contactUsSection.setOnClickListener {
                EmailManager.contactUs(context)
            }

            val notificationsTime = preferencesManager.getNotificationsTime()
            binding.tvNotificationsTime.text = notificationsTime
            binding.tvNotificationsTime.setOnClickListener {
                val fragmentManager = fragmentManager
                fragmentManager?.let {
                    val timeSplit = notificationsTime.split(":")
                    val picker = MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(timeSplit[0].toInt())
                        .setMinute(timeSplit[1].toInt())
                        .build()

                    picker.addOnPositiveButtonClickListener {
                        val hour = picker.hour
                        val minute = picker.minute
                        val timeString = "$hour:$minute"

                        binding.tvNotificationsTime.text = timeString
                        preferencesManager.setNotificationsTime(timeString)
                    }

                    picker.show(fragmentManager, "notification_time_picket")
                }
            }
        }
    }
}