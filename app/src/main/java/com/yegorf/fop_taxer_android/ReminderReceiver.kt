package com.yegorf.fop_taxer_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val description = intent?.extras?.getString(ReminderManager.DESCRIPTION_EXTRA_NAME)
        Timber.d("Remind event received: $description")

        context?.let {
            NotificationsManager(it).sendResultNotification(
                description ?: context.getString(R.string.default_notification_description)
            )
        }
    }
}