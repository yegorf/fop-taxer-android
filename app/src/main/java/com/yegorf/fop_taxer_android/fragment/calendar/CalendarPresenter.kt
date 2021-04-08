package com.yegorf.fop_taxer_android.fragment.calendar

import android.content.Context
import com.yegorf.fop_taxer_android.presentation.Presenter

interface CalendarPresenter : Presenter<CalendarView> {

    fun getCalendar(context: Context)
}