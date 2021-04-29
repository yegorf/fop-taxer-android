package com.yegorf.fop_taxer_android

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.yegorf.fop_taxer_android.data.TaxEvent
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ReminderManager(private val context: Context) {

    companion object {
        const val DESCRIPTION_EXTRA_NAME = "DESCRIPTION_EXTRA_NAME"
        const val NOTIFICATION_TIME_HOURS = 12 * 60 * 60 * 1000
    }

    fun setReminderForEvent(event: TaxEvent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra(DESCRIPTION_EXTRA_NAME, event.description)

        val pendingIntent = PendingIntent.getBroadcast(context, event.id, intent, 0)

        val timeMills = getReminderTimeMills(event.date) + NOTIFICATION_TIME_HOURS
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(timeMills, pendingIntent),
                pendingIntent
            )
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeMills,
                pendingIntent
            )
        }

        Timber.d("Reminder added (id: ${event.id}, timestamp: $timeMills)")
    }

    fun disableReminderForEvent(requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        alarmManager.cancel(pendingIntent)

        Timber.d("Reminder disabled (id: $requestCode)")
    }

    private fun getReminderTimeMills(dateString: String): Long {
        val date = SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString)
        return date!!.time
    }
}