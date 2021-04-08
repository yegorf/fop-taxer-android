package com.yegorf.fop_taxer_android.fragment.calendar

import android.content.Context
import com.google.gson.Gson
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.presentation.AbstractPresenter
import com.yegorf.fop_taxer_android.tools.DateHelper
import java.util.*

class CalendarPresenterImpl : AbstractPresenter<CalendarView>(), CalendarPresenter {

    override fun onCreate(view: CalendarView) {
        super.onCreate(view)
        view.setDate(DateHelper.getCurrentDate("dd.MM.yyyy"))
    }

    override fun getCalendar(context: Context) {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val calendarFileName = "calendar$year.json"

        val inputStream = context.assets?.open(calendarFileName)
        val scanner = Scanner(inputStream)
        val builder = StringBuilder()
        while (scanner.hasNext()) {
            builder.append(scanner.nextLine())
        }
        val events = Gson().fromJson(builder.toString(), Array<TaxEvent>::class.java).asList()
        view?.setCalendar(events)
    }
}