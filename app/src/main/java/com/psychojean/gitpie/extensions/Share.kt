package com.psychojean.gitpie.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

fun Activity.openShareIntent(link: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, link)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}