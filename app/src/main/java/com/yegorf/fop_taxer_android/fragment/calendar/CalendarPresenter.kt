package com.yegorf.fop_taxer_android.fragment.calendar

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.presentation.Presenter

interface CalendarPresenter : Presenter<CalendarView> {

    fun getCalendar(context: Context)

    fun setEventAsDone(event: TaxEvent)

    fun changeEventAlarm(event: TaxEvent, context: Context)
}