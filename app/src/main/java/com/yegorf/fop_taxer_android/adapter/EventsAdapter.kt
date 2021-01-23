package com.yegorf.fop_taxer_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yegorf.fop_taxer_android.R
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.databinding.ItemTaxEventBinding
import com.yegorf.fop_taxer_android.tools.DateHelper

class EventsAdapter(private val data: List<TaxEvent>) :
    RecyclerView.Adapter<EventsAdapter.EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val binding = ItemTaxEventBinding.inflate(LayoutInflater.from(parent.context))
        return EventHolder(binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    class EventHolder(private val binding: ItemTaxEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: TaxEvent) {
            binding.tvDescription.text = event.description
            binding.tvDate.text = event.date

            val indicatorColor = when (DateHelper.getEventStatus(event.date)) {
                DateHelper.DateStatus.EXPECTED -> R.drawable.circle_indicator_grey
                DateHelper.DateStatus.TODAY -> R.drawable.circle_indicator_red
                DateHelper.DateStatus.PASSED -> R.drawable.circle_indicator_green
            }
            binding.dateIndicator.background = binding.root.context.getDrawable(indicatorColor)
        }
    }
}