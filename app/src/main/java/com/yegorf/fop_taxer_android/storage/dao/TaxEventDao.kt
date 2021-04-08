package com.yegorf.fop_taxer_android.storage.dao

import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.storage.`object`.TaxEventObject
import io.realm.Realm
import io.realm.kotlin.where

object TaxEventDao : RealmDao<TaxEventObject>() {

    fun insert(taxEventObject: TaxEventObject) {
        Realm.getDefaultInstance().executeTransaction {
            taxEventObject.id = generateId(TaxEventObject::class.java, it)
            it.insert(taxEventObject)
        }
    }

    fun insertInit(realm: Realm, taxEventObject: TaxEventObject) {
        taxEventObject.id = generateId(TaxEventObject::class.java, realm)
        realm.insert(taxEventObject)
    }

    fun getAll(): List<TaxEvent> {
        return Realm.getDefaultInstance()
            .where<TaxEventObject>()
            .findAll()
            .map { TaxEvent(it.date, it.description) }
    }
}