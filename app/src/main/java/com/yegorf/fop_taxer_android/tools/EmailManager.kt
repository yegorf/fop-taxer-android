package com.yegorf.fop_taxer_android.tools

import android.content.Context
import android.content.Intent
import android.net.Uri


object EmailManager {

    private const val CONTACT_US_EMAIL = "foptaxer@gmail.com"

    fun contactUs(context: Context) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$CONTACT_US_EMAIL")
        context.startActivity(intent)
    }
}