package com.yegorf.fop_taxer_android.fragment.calendar

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.yegorf.fop_taxer_android.PreferencesManager
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.presentation.AbstractPresenter
import com.yegorf.fop_taxer_android.storage.`object`.TaxEventObject
import com.yegorf.fop_taxer_android.storage.dao.TaxEventDao
import com.yegorf.fop_taxer_android.tools.DateHelper

class CalendarPresenterImpl : AbstractPresenter<CalendarView>(), CalendarPresenter {

    override fun onCreate(view: CalendarView) {
        super.onCreate(view)
        view.setDate(DateHelper.getCurrentDate("dd.MM.yyyy"))
    }

    override fun getCalendar(context: Context) {
        val taxGroup = PreferencesManager(context).getTaxGroup()
        val events = TaxEventDao.getEventsByGroup(taxGroup)
        view?.setCalendar(events)
    }

    override fun setEventAsDone(event: TaxEvent) {
        TaxEventDao.update(TaxEventObject(event))
    }

    override fun changeEventAlarm(event: TaxEvent) {
        TaxEventDao.update(TaxEventObject(event))
    }
}