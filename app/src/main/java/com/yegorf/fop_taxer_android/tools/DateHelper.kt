package com.yegorf.fop_taxer_android.tools

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateHelper {

    private const val WARNING_INTERVAL = 5

    fun getEventStatus(date: String): DateStatus {
        val eventDate = SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(date)
        val currentDate = Date()

        eventDate?.let {
            val difference = getDifferenceInDays(currentDate, eventDate)
            return when {
                difference < 0 -> DateStatus.PASSED
                difference == 0L -> DateStatus.TODAY
                difference <= WARNING_INTERVAL -> DateStatus.SOON
                difference > WARNING_INTERVAL -> DateStatus.EXPECTED
                else -> DateStatus.EXPECTED
            }
        }
        return DateStatus.EXPECTED
    }

    private fun getDifferenceInDays(date1: Date, date2: Date): Long {
        val difference = date2.time - date1.time
        return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
    }

    fun getCurrentDate(format: String): String {
        return SimpleDateFormat(format, Locale.US).format(Date())
    }

    enum class DateStatus {
        EXPECTED,
        SOON,
        TODAY,
        PASSED
    }
}