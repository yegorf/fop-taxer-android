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
    }

    fun setReminderForEvent(event: TaxEvent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra(DESCRIPTION_EXTRA_NAME, event.description)

        val pendingIntent = PendingIntent.getBroadcast(context, event.id, intent, 0)

        val dateTimeMills = getReminderDateMills(event.date) + getReminderTimeMills()
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(dateTimeMills, pendingIntent),
                pendingIntent
            )
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                dateTimeMills,
                pendingIntent
            )
        }

        Timber.d("Reminder added (id: ${event.id}, timestamp: $dateTimeMills)")
    }

    fun disableReminderForEvent(requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        alarmManager.cancel(pendingIntent)

        Timber.d("Reminder disabled (id: $requestCode)")
    }

    private fun getReminderDateMills(dateString: String): Long {
        val date = SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString)
        return date!!.time
    }

    private fun getReminderTimeMills(): Long {
        val time = PreferencesManager(context).getNotificationsTime()
        val split = time.split(":")
        val hours = split[0].toLong() * 60 * 60 * 1000
        val minutes = split[1].toLong() * 60 * 1000
        return hours + minutes
    }
}