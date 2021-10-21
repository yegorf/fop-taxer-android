package com.yegorf.fop_taxer_android.fragment.calendar

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yegorf.fop_taxer_android.R
import com.yegorf.fop_taxer_android.adapter.EventsAdapter
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.databinding.FragmentCalendarBinding
import com.yegorf.fop_taxer_android.tools.copyToClipboard


class CalendarFragment : Fragment(), CalendarView, EventsAdapter.TaxEventListener {

    private lateinit var binding: FragmentCalendarBinding
    private val presenter: CalendarPresenter = CalendarPresenterImpl()
    private lateinit var adapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater)
        presenter.onCreate(this)
        context?.let {
            presenter.getCalendar(it)
        }
        binding.ivUserManual.setOnClickListener {
            showUserManualPopup()
        }

        return binding.root
    }

    private fun showUserManualPopup() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.user_manual_title))
                .setMessage(getString(R.string.user_manual_description))
                .setPositiveButton(
                    getString(R.string.user_manual_close_button)
                ) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    override fun setDate(date: String) {
        binding.tvCurrentDate.text = date
    }

    override fun setCalendar(events: List<TaxEvent>) {
        adapter = EventsAdapter(events, this)
        binding.rvEvents.layoutManager = LinearLayoutManager(context)
        binding.rvEvents.adapter = adapter
        binding.rvEvents.addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
    }

    override fun onEventClick(event: TaxEvent, adapterPosition: Int) {
        presenter.setEventAsDone(event)
        adapter.notifyItemChanged(adapterPosition, EventsAdapter.Payload.DONE_STATUS_UPDATE)
    }

    override fun onEventLongTap(event: TaxEvent) {
        activity?.let {
            copyToClipboard(it, "${event.date} - ${event.description}")
        }
    }

    override fun onEventAlarmClick(event: TaxEvent, adapterPosition: Int) {
        presenter.changeEventAlarm(event, context!!)
        adapter.notifyItemChanged(adapterPosition, EventsAdapter.Payload.ALARM_STATUS_UPDATE)
    }
}