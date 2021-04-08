package com.yegorf.fop_taxer_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yegorf.fop_taxer_android.R
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.databinding.ItemTaxEventBinding
import com.yegorf.fop_taxer_android.tools.DateHelper

class EventsAdapter(private val data: List<TaxEvent>, private val listener: TaxEventListener) :
    RecyclerView.Adapter<EventsAdapter.EventHolder>() {

    object Payload {
        const val DONE_STATUS_UPDATE = "DONE_STATUS_UPDATE"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val binding = ItemTaxEventBinding.inflate(LayoutInflater.from(parent.context))
        return EventHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            if (payloads[0] == Payload.DONE_STATUS_UPDATE) {
                holder.bindDoneStatus(data[position])
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount() = data.size

    class EventHolder(
        private val binding: ItemTaxEventBinding,
        private val listener: TaxEventListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: TaxEvent) {
            binding.tvDescription.text = event.description
            binding.tvDate.text = event.date
            setEventStatusView(event)

            val eventStatus = DateHelper.getEventStatus(event)
            if (eventStatus == DateHelper.DateStatus.EXPIRED || eventStatus == DateHelper.DateStatus.DONE) { //todo: temporary solution
                itemView.setOnLongClickListener {
                    event.isDone = !event.isDone
                    listener.onEventLongTap(event, adapterPosition)
                    return@setOnLongClickListener true
                }
            }
        }

        fun bindDoneStatus(event: TaxEvent) {
            setEventStatusView(event)
        }

        private fun setEventStatusView(event: TaxEvent) {
            val indicatorColor = when (DateHelper.getEventStatus(event)) {
                DateHelper.DateStatus.EXPECTED -> R.drawable.circle_indicator_grey
                DateHelper.DateStatus.SOON -> R.drawable.circle_indicator_yellow
                DateHelper.DateStatus.EXPIRED -> R.drawable.circle_indicator_red
                DateHelper.DateStatus.DONE -> R.drawable.circle_indicator_green
            }
            binding.dateIndicator.background =
                ContextCompat.getDrawable(binding.root.context, indicatorColor)
        }
    }

    interface TaxEventListener {

        fun onEventLongTap(event: TaxEvent, adapterPosition: Int)
    }
}