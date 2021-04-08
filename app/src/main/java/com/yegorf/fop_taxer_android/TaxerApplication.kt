package com.yegorf.fop_taxer_android

import android.app.Application
import android.util.Log
import com.yegorf.fop_taxer_android.storage.RealmHelper
import com.yegorf.fop_taxer_android.storage.dao.TaxEventDao

class TaxerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RealmHelper.init(this)

        TaxEventDao.getAll().forEach {
            Log.d("testing", "${it.date} - ${it.description}")
        }
    }
}