package com.noemi.android.trendingkotlin

import android.os.Build
import com.noemi.android.trendingkotlin.ui.MainActivity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class MainActivityUnitTest {

    private var mainActivity: MainActivity? = null

    @Before
    fun setUp() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()
    }

    @Test
    fun testMainContext() {
        val context = RuntimeEnvironment.systemContext
        Assert.assertEquals("android", context.packageName)
    }

    @After
    fun tearDown() {
        mainActivity = null
    }
}