package com.yegorf.fop_taxer_android.adapter

import android.view.LayoutInflater
import android.view.View
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
        const val ALARM_STATUS_UPDATE = "ALARM_STATUS_UPDATE"
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
            when (payloads[0]) {
                Payload.DONE_STATUS_UPDATE -> holder.bindDoneStatus(data[position])
                Payload.ALARM_STATUS_UPDATE -> holder.bindAlarmStatus(data[position])
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
            bindDoneStatus(event)

            val eventStatus = DateHelper.getEventStatus(event)
            if (eventStatus == DateHelper.DateStatus.EXPIRED || eventStatus == DateHelper.DateStatus.DONE) { //todo: temporary solution
                itemView.setOnLongClickListener {
                    event.isDone = !event.isDone
                    listener.onEventLongTap(event, adapterPosition)
                    return@setOnLongClickListener true
                }
            }

            binding.ivAlarm.setOnClickListener {
                event.isAlarmOn = !event.isAlarmOn
                listener.onEventAlarmClick(event, adapterPosition)
            }
        }

        fun bindDoneStatus(event: TaxEvent) {
            val eventStatus = DateHelper.getEventStatus(event)
            val indicatorColor = when (eventStatus) {
                DateHelper.DateStatus.EXPECTED -> R.drawable.circle_indicator_grey
                DateHelper.DateStatus.SOON -> R.drawable.circle_indicator_yellow
                DateHelper.DateStatus.EXPIRED -> R.drawable.circle_indicator_red
                DateHelper.DateStatus.DONE -> R.drawable.circle_indicator_green
            }
            if (eventStatus == DateHelper.DateStatus.DONE || eventStatus == DateHelper.DateStatus.EXPIRED) {
                binding.ivAlarm.visibility = View.GONE
            } else {
                bindAlarmStatus(event)
            }
            binding.dateIndicator.background =
                ContextCompat.getDrawable(binding.root.context, indicatorColor)
        }

        fun bindAlarmStatus(event: TaxEvent) {
            val alarmIcon = if (event.isAlarmOn) {
                R.drawable.ic_alarm_on
            } else {
                R.drawable.ic_alarm_off
            }
            binding.ivAlarm.setImageResource(alarmIcon)
        }
    }

    interface TaxEventListener {

        fun onEventLongTap(event: TaxEvent, adapterPosition: Int)

        fun onEventAlarmClick(event: TaxEvent, adapterPosition: Int)
    }
}