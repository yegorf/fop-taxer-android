package com.yegorf.fop_taxer_android.fragment.calendar

import androidx.fragment.app.FragmentActivity
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.presentation.Presenter

interface CalendarPresenter : Presenter<CalendarView> {

    fun getCalendar(activity: FragmentActivity)

    fun setEventAsDone(event: TaxEvent)
}