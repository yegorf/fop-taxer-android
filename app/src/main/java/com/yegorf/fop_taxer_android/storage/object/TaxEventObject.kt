package com.yegorf.fop_taxer_android.storage.`object`

import com.yegorf.fop_taxer_android.data.TaxEvent
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TaxEventObject() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    lateinit var date: String
    lateinit var description: String
    var group: Int = 0
    var isDone: Boolean = false
    var isAlarmOn: Boolean = false

    constructor(taxEvent: TaxEvent): this() {
        id = taxEvent.id
        date = taxEvent.date
        description = taxEvent.description
        group = taxEvent.group
        isDone = taxEvent.isDone
        isAlarmOn = taxEvent.isAlarmOn
    }
}