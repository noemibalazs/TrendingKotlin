package com.noemi.android.trendingkotlin

import android.content.SharedPreferences
import android.os.Build
import com.nhaarman.mockito_kotlin.mock
import com.noemi.android.trendingkotlin.preference.PreferenceRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.NullPointerException

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class PreferenceRepositoryUnitTest {

    private lateinit var preferencesRepository: PreferenceRepository
    private val sharedPreferences: SharedPreferences = mock()
    private val repositoryID = 12
    private val fullName = "kotlin/coroutines"

    @Before
    fun setUp() {
        preferencesRepository = PreferenceRepository(sharedPreferences)
    }

    @Test(expected = NullPointerException::class)
    fun testRepositoryIdShouldPass() {
        preferencesRepository.repositoryID = repositoryID
        val expected = preferencesRepository.repositoryID
        assertEquals(expected, repositoryID)
    }

    @Test(expected = NullPointerException::class)
    fun testRepositoryIdShouldFailed() {
        preferencesRepository.repositoryID = repositoryID
        val expected = preferencesRepository.repositoryID
        assertNotEquals(expected, repositoryID - 1)
    }

    @Test(expected = NullPointerException::class)
    fun testRepositoryNameShouldPass() {
        preferencesRepository.repositoryFullName = fullName
        val expected = preferencesRepository.repositoryFullName
        assertEquals(expected, repositoryID)
    }

    @Test(expected = NullPointerException::class)
    fun testRepositoryNameShouldFailed() {
        preferencesRepository.repositoryFullName = fullName
        val expected = preferencesRepository.repositoryFullName
        assertNotEquals(expected, "kotlin")
    }
}