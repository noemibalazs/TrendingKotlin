package com.noemi.android.trendingkotlin

import android.os.Build
import com.noemi.android.trendingkotlin.api.model.Repository
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class RepositoryUnitTest {

    private val repository = Repository(
        id = 12,
        name = "kotlinx.coroutines",
        fullName = "kotlin/coroutines",
        description = "Library support for Kotlin coroutines",
        updated = "2021-07-21",
        stargazersCount = 21
    )

    @Test
    fun testShouldPass() {
        val expected = Repository(
            id = 12,
            name = "kotlinx.coroutines",
            fullName = "kotlin/coroutines",
            description = "Library support for Kotlin coroutines",
            updated = "2021-07-21",
            stargazersCount = 21
        )

        assertEquals(expected, repository)
    }

    @Test
    fun testShouldFailed(){
        val expected = Repository(
            id = 21,
            name = "kotlinx.coroutines",
            fullName = "kotlin/coroutines",
            description = "Library support for Kotlin coroutines",
            updated = "2021-07-21",
            stargazersCount = 21
        )

        assertNotEquals(expected, repository)
    }
}