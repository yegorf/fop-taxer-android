package com.yegorf.fop_taxer_android.fragment

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.yegorf.fop_taxer_android.adapter.EventsAdapter
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.databinding.FragmentCalendarBinding
import com.yegorf.fop_taxer_android.tools.DateHelper
import java.util.*


class CalendarFragment : Fragment() {

    private val calendarFileName = "calendar2020.json"
    private lateinit var binding: FragmentCalendarBinding;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater)
        setDate()
        setEventsList(getCalendar())
        return binding.root
    }

    private fun setDate() {
        binding.tvCurrentDate.text = DateHelper.getCurrentDate()
    }

    private fun setEventsList(events: List<TaxEvent>) {
        val adapter = EventsAdapter(events)
        binding.rvEvents.layoutManager = LinearLayoutManager(context)
        binding.rvEvents.adapter = adapter
        binding.rvEvents.addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
    }

    private fun getCalendar(): List<TaxEvent> {
        val inputStream = context?.assets?.open(calendarFileName)
        val scanner = Scanner(inputStream)
        val builder = StringBuilder()
        while (scanner.hasNext()) {
            builder.append(scanner.nextLine())
        }
        return Gson().fromJson(builder.toString(), Array<TaxEvent>::class.java).asList()
    }
}