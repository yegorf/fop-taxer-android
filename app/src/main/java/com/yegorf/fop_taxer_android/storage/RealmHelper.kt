package com.yegorf.fop_taxer_android.storage

import android.content.Context
import com.google.gson.Gson
import com.yegorf.fop_taxer_android.data.TaxEvent
import com.yegorf.fop_taxer_android.storage.`object`.TaxEventObject
import com.yegorf.fop_taxer_android.storage.dao.TaxEventDao
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*

object RealmHelper {

    private const val REALM_NAME = "db.realm"

    fun init(context: Context) {
        Realm.init(context)
        Realm.setDefaultConfiguration(getConfiguration(context))
    }

    private fun getConfiguration(context: Context): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name(REALM_NAME)
            .allowWritesOnUiThread(true)
            .initialData { realm ->
                realm.deleteAll()
                getEventsOnCurrentYear(context)
                    .map { event ->
                        TaxEventObject(event)
                    }
                    .forEach { eventObject ->
                        TaxEventDao.insert(realm, eventObject)
                    }
            }
            .build()
    }

    private fun getEventsOnCurrentYear(context: Context): List<TaxEvent> {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val calendarFileName = "calendar$year.json"

        val inputStream = context.assets?.open(calendarFileName)
        val scanner = Scanner(inputStream)
        val builder = StringBuilder()
        while (scanner.hasNext()) {
            builder.append(scanner.nextLine())
        }
        return Gson().fromJson(builder.toString(), Array<TaxEvent>::class.java).asList()
    }
}