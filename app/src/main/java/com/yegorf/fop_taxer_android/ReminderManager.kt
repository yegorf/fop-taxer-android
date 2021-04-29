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

    private fun setReminderForEvent(event: TaxEvent) {
        setReminder(getReminderTimeMills(event.date), event.id)
    }

    private fun setReminder(timeMills: Long, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)

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

        Timber.d("Reminder added (id: $requestCode, timestamp: $timeMills)")
    }

    private fun getReminderTimeMills(dateString: String): Long {
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dateString)
        return date!!.time
    }
}