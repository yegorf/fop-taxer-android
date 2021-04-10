package com.yegorf.fop_taxer_android.fragment.calendar

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

    override fun getCalendar(activity: FragmentActivity) {
        val taxGroup = PreferencesManager(activity).getTaxGroup()
        val events = TaxEventDao.getEventsByGroup(taxGroup)
        view?.setCalendar(events)
    }

    override fun setEventAsDone(event: TaxEvent) {
        TaxEventDao.markAsDone(TaxEventObject(event))
    }
}