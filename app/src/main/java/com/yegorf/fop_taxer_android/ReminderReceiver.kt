package com.yegorf.fop_taxer_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ReminderReceiver : BroadcastReceiver() {

    private val TAG = ReminderReceiver::class.java.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "receive")
        context?.let {
            NotificationsManager(it).sendResultNotification("Time to pay taxes!")
        }
    }
}