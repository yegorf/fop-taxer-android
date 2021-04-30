package com.yegorf.fop_taxer_android

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    companion object {
        const val SHARED_PREFERENCES_NAME = "taxer_shared_preferences"
        const val DEFAULT_NOTIFICATIONS_TIME = "12:00"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    object Key {
        const val TAX_GROUP = "TAX_GROUP"
        const val NOTIFICATIONS_SOUND_ON = "NOTIFICATIONS_SOUND_ON"
        const val NOTIFICATIONS_TIME = "NOTIFICATIONS_TIME"
    }

    fun setTaxGroup(group: Int) {
        preferences.edit()
            .putInt(Key.TAX_GROUP, group)
            .apply()
    }

    fun getTaxGroup(): Int {
        return preferences.getInt(Key.TAX_GROUP, 3)
    }

    fun setNotificationsSoundOn(notificationsSoundOn: Boolean) {
        preferences.edit()
            .putBoolean(Key.NOTIFICATIONS_SOUND_ON, notificationsSoundOn)
            .apply()
    }

    fun isNotificationsSoundOn(): Boolean {
        return preferences.getBoolean(Key.NOTIFICATIONS_SOUND_ON, true)
    }

    fun setNotificationsTime(time: String) {
        preferences.edit()
            .putString(Key.NOTIFICATIONS_TIME, time)
            .apply()
    }

    fun getNotificationsTime(): String {
        return preferences.getString(Key.NOTIFICATIONS_TIME, DEFAULT_NOTIFICATIONS_TIME)
            ?: DEFAULT_NOTIFICATIONS_TIME
    }
}