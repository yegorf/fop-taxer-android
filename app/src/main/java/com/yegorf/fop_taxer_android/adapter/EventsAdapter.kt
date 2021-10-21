package com.yegorf.fop_taxer_android.adapter

import android.graphics.Paint
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
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
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
            setOnClickListeners(event)
        }

        private fun setOnClickListeners(event: TaxEvent) {
            val eventStatus = DateHelper.getEventStatus(event)
            if (eventStatus == DateHelper.DateStatus.EXPIRED || eventStatus == DateHelper.DateStatus.DONE) { //todo: temporary solution
                itemView.setOnClickListener {
                    event.apply { isDone = !isDone }
                    listener.onEventClick(event, adapterPosition)
                }
            }

            binding.ivAlarm.setOnClickListener {
                event.apply { isAlarmOn = !isAlarmOn }
                listener.onEventAlarmClick(event, adapterPosition)
            }
        }

        fun bindDoneStatus(event: TaxEvent) {
            val eventStatus = DateHelper.getEventStatus(event)
            setTextCrossing(eventStatus)
            setStatusText(eventStatus)

            if (eventStatus == DateHelper.DateStatus.DONE || eventStatus == DateHelper.DateStatus.EXPIRED) {
                binding.ivAlarm.visibility = View.GONE
            } else {
                bindAlarmStatus(event)
            }
        }

        private fun setStatusText(status: DateHelper.DateStatus) {
            val params = when (status) {
                DateHelper.DateStatus.EXPECTED -> Triple(
                    R.string.event_status_waiting,
                    R.color.lightGrey,
                    R.drawable.bg_event_status_expected
                )
                DateHelper.DateStatus.SOON -> Triple(
                    R.string.event_status_deadline,
                    R.color.warningYellow,
                    R.drawable.bg_event_status_soon
                )
                DateHelper.DateStatus.EXPIRED -> Triple(
                    R.string.event_status_expired,
                    R.color.coralRed,
                    R.drawable.bg_event_status_expired
                )
                DateHelper.DateStatus.DONE -> Triple(
                    R.string.event_status_done,
                    R.color.lightGreen,
                    R.drawable.bg_event_status_done
                )
            }

            binding.tvStatus.apply {
                this.text = itemView.resources.getText(params.first)
                this.background = ContextCompat.getDrawable(itemView.context, params.third)
                this.setTextColor(ContextCompat.getColor(itemView.context, params.second))
            }
        }

        private fun setTextCrossing(status: DateHelper.DateStatus) {
            if (!binding.tvDescription.paint.isStrikeThruText && status == DateHelper.DateStatus.DONE) {
                binding.tvDescription.paintFlags =
                    binding.tvDescription.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.tvDescription.paintFlags =
                    binding.tvDescription.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
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

        fun onEventClick(event: TaxEvent, adapterPosition: Int)

        fun onEventAlarmClick(event: TaxEvent, adapterPosition: Int)
    }
}