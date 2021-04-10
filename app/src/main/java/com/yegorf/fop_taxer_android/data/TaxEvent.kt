package com.yegorf.fop_taxer_android.data

data class TaxEvent(
    val id: Int,
    val date: String,
    val description: String,
    var isDone: Boolean,
    var group: Int
)
