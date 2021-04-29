package com.yegorf.fop_taxer_android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class NotificationsManager(private val context: Context) {

    companion object {
        const val REMINDER_CHANNEL_ID = "REMINDER_CHANNEL_ID"
        const val REMINDER_CHANNEL_NAME = "REMINDER_CHANNEL_NAME"
    }

    fun sendResultNotification(description: String) {
        val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE)
                as NotificationManager
        createNotificationChannel(manager)

        val notificationBuilder = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_app)

        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        if (PreferencesManager(context).isNotificationsSoundOn()) {
            notificationBuilder.setSound(sound)
        } else {
            notificationBuilder.setNotificationSilent()
        }

        manager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(REMINDER_CHANNEL_ID) == null) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        REMINDER_CHANNEL_ID,
                        REMINDER_CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
            }
        }
    }
}
