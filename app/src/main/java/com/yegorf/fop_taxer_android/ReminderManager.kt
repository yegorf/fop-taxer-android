package com.yegorf.fop_taxer_android

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.yegorf.fop_taxer_android.storage.dao.TaxEventDao
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ReminderManager(private val context: Context) {

    private fun setReminders(taxGroup: Int) {
        TaxEventDao.getEventsByGroup(taxGroup)
            .forEach { event ->
                val dateString = event.date
                val date = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dateString)
                date?.let {
                    val dateMills = date.time
                    setReminder(dateMills)
                    Timber.d("Reminder for event ${event.description} set on $dateString")
                }
            }
    }

    fun setReminder(timeMills: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

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
    }
}