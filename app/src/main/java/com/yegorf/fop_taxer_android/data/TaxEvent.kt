package com.yegorf.fop_taxer_android.data

import com.yegorf.fop_taxer_android.storage.`object`.TaxEventObject

data class TaxEvent(
    val id: Int,
    val date: String,
    val description: String,
    var isDone: Boolean,
    var group: Int,
    var isAlarmOn: Boolean
) {
    constructor(tax: TaxEventObject) : this(
        tax.id,
        tax.date,
        tax.description,
        tax.isDone,
        tax.group,
        tax.isAlarmOn
    )
}
