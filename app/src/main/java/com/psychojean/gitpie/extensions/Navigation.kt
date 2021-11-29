package com.psychojean.gitpie.extensions

import android.app.Activity
import android.content.Intent
import com.psychojean.gitpie.ui.MainActivity

fun Activity.toMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
}