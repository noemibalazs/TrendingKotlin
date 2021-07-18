package com.noemi.android.trendingkotlin

import android.os.Build
import com.noemi.android.trendingkotlin.api.model.Repository
import com.noemi.android.trendingkotlin.room.RepositoryEntity
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class RepositoryEntityUnitTest {

    private val entity = RepositoryEntity(
        id = 12,
        name = "kotlinx.coroutines",
        fullName = "kotlin/coroutines",
        description = "Library support for Kotlin coroutines",
        updated = "2021-07-21",
        stargazers = 21
    )

    @Test
    fun testShouldPass() {
        val expected = RepositoryEntity(
            id = 12,
            name = "kotlinx.coroutines",
            fullName = "kotlin/coroutines",
            description = "Library support for Kotlin coroutines",
            updated = "2021-07-21",
            stargazers = 21
        )

        assertEquals(expected, entity)
    }

    @Test
    fun testShouldFailed(){
        val expected = RepositoryEntity(
            id = 21,
            name = "kotlinx.coroutines",
            fullName = "kotlin/coroutines",
            description = "Library support for Kotlin coroutines",
            updated = "2021-07-21",
            stargazers = 21
        )

        assertNotEquals(expected, entity)
    }

}