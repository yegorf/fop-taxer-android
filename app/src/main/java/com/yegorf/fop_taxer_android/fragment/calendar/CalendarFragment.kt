package com.yegorf.fop_taxer_android.fragment.calendar

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yegorf.fop_taxer_android.adapter.EventsAdapter
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.databinding.FragmentCalendarBinding


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
        activity?.let {
            presenter.getCalendar(it)
        }
        return binding.root
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

    override fun onEventLongTap(event: TaxEvent, adapterPosition: Int) {
        presenter.setEventAsDone(event)
        adapter.notifyItemChanged(adapterPosition, EventsAdapter.Payload.DONE_STATUS_UPDATE)
    }
}