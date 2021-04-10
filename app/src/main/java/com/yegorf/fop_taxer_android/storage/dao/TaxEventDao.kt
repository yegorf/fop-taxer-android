package com.yegorf.fop_taxer_android.storage.dao

import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.storage.`object`.TaxEventObject
import io.realm.Realm
import io.realm.kotlin.where

object TaxEventDao : RealmDao<TaxEventObject>() {

    fun insert(realm: Realm, taxEventObject: TaxEventObject) {
        taxEventObject.id = generateId(TaxEventObject::class.java, realm)
        realm.insert(taxEventObject)
    }

    fun markAsDone(taxEventObject: TaxEventObject) {
        Realm.getDefaultInstance().executeTransaction {
            it.insertOrUpdate(taxEventObject)
        }
    }

    fun getAll(): List<TaxEvent> {
        return Realm.getDefaultInstance()
            .where<TaxEventObject>()
            .findAll()
            .map { TaxEvent(it.id, it.date, it.description, it.isDone, it.group) }
    }

    fun getEventsByGroup(group: Int): List<TaxEvent> {
        return Realm.getDefaultInstance()
            .where<TaxEventObject>()
            .equalTo("group", group)
            .findAll()
            .map { TaxEvent(it.id, it.date, it.description, it.isDone, it.group) }
    }
}