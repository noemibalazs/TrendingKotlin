package com.noemi.android.trendingkotlin.util

import android.os.SystemClock
import android.view.View

abstract class OneTimeClickListener(private val limit: Int = 900) : View.OnClickListener {

    abstract fun oneTimeClick(v: View)
    private var threshHold = 0L

    override fun onClick(view: View) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - threshHold > limit) {
            threshHold = currentTime
            oneTimeClick(view)
        }
    }
}