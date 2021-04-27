package com.yegorf.fop_taxer_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("Remind event received")
        context?.let {
            NotificationsManager(it).sendResultNotification("Time to pay taxes!")
        }
    }
}