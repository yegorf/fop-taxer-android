package com.yegorf.fop_taxer_android.storage.`object`

import com.yegorf.fop_taxer_android.data.TaxEvent
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TaxEventObject() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    lateinit var date: String
    lateinit var description: String

    constructor(taxEvent: TaxEvent): this() {
        date = taxEvent.date
        description = taxEvent.description
    }
}