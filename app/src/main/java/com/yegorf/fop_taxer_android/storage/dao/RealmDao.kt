package com.yegorf.fop_taxer_android.storage.dao

import io.realm.Realm
import io.realm.RealmObject

open class RealmDao<T> where T : RealmObject {

    protected fun generateId(clazz: Class<T>, realm: Realm): Int {
        val max = realm.where(clazz).max("id")
        return if (max != null) {
            max.toInt() + 1
        } else {
            0
        }
    }
}