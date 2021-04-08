package com.yegorf.fop_taxer_android.fragment.calendar

import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.presentation.View

interface CalendarView : View {

    fun setDate(date: String)

    fun setCalendar(events: List<TaxEvent>)
}