package com.noemi.android.trendingkotlin.util

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.noemi.android.trendingkotlin.R
import java.text.SimpleDateFormat
import java.util.*

fun Activity.launchActivity(dest: Class<*>) {
    startActivity(Intent(this, dest))
    overridePendingTransition(R.anim.next_enter, R.anim.next_leave)
}

fun String.displayData(): String {
    val sdfInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val sdfOutPut = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = sdfInput.parse(this)
    return date?.let { sdfOutPut.format(it) } ?: ""
}

fun Activity.showToastToUser(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}