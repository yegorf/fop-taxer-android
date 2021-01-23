package com.yegorf.fop_taxer_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.databinding.ItemTaxEventBinding

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
        }
    }
}