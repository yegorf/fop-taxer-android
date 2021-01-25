package com.yegorf.fop_taxer_android

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

class PreferencesManager(activity: FragmentActivity) {

    private val preferences: SharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)

    companion object {
        private const val NOTIFICATIONS_ON_KEY = "NOTIFICATIONS_ON_KEY"
    }

    fun setNotificationsOn(notificationsOn: Boolean) {
        preferences.edit()
            .putBoolean(NOTIFICATIONS_ON_KEY, notificationsOn)
            .apply()
    }

    fun isShowNotifications(): Boolean {
        return preferences.getBoolean(NOTIFICATIONS_ON_KEY, true)
    }
}