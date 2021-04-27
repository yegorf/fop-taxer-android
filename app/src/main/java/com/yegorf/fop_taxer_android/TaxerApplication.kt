package com.yegorf.fop_taxer_android

import android.app.Application
import com.yegorf.fop_taxer_android.storage.RealmHelper
import timber.log.Timber

class TaxerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        RealmHelper.init(this)
    }
}