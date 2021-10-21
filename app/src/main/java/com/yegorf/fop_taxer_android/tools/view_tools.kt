package com.yegorf.fop_taxer_android.tools

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.yegorf.fop_taxer_android.R

fun copyToClipboard(activity: Activity, text: String) {
    val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(
        activity,
        activity.getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT
    ).show()
}