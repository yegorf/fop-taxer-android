package com.yegorf.fop_taxer_android.tools

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    private const val DATE_FORMAT = "dd.MM.yyyy"

    fun getEventStatus(date: String): DateStatus {
        val eventDate = SimpleDateFormat(DATE_FORMAT, Locale.US).parse(date)
        val currentDate = Date()
        return when {
            eventDate.after(currentDate) -> DateStatus.EXPECTED
            eventDate.before(currentDate) -> DateStatus.PASSED
            else -> DateStatus.TODAY
        }
    }

    fun getCurrentDate(): String {
        return SimpleDateFormat(DATE_FORMAT, Locale.US).format(Date())
    }

    enum class DateStatus {
        EXPECTED,
        TODAY,
        PASSED
    }
}