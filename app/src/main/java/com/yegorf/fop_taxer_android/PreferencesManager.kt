package com.yegorf.fop_taxer_android

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

class PreferencesManager(activity: FragmentActivity) {

    private val preferences: SharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)

    object Key {
        const val NOTIFICATIONS_ON = "NOTIFICATIONS_ON"
        const val TAX_GROUP = "TAX_GROUP"
    }

    fun setNotificationsOn(notificationsOn: Boolean) {
        preferences.edit()
            .putBoolean(Key.NOTIFICATIONS_ON, notificationsOn)
            .apply()
    }

    fun isShowNotifications(): Boolean {
        return preferences.getBoolean(Key.NOTIFICATIONS_ON, true)
    }

    fun setTaxGroup(group: Int) {
        preferences.edit()
            .putInt(Key.TAX_GROUP, group)
            .apply()
    }

    fun getTaxGroup(): Int {
        return preferences.getInt(Key.TAX_GROUP, 3)
    }
}