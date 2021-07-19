package com.noemi.android.trendingkotlin

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.nhaarman.mockito_kotlin.mock
import com.noemi.android.trendingkotlin.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class LocaleRepositoryUnitTest {

    private lateinit var repositoryDB: RepositoryDB
    private lateinit var repositoryDAO: RepositoryDAO
    private val context: Context = mock()
    private val repositoryEntity: RepositoryEntity = mock()


    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repositoryDB = Room.inMemoryDatabaseBuilder(context, RepositoryDB::class.java).build()
        repositoryDAO = repositoryDB.getRepositoryDAO()
    }


    @Test
    @Throws(Exception::class)
    fun testInsertRepositoryEntitiesShouldPass() {
        val entities = mutableListOf(repositoryEntity)
        CoroutineScope(Dispatchers.IO).launch {
            repositoryDAO.insertRepositoryList(entities)
            val filter = repositoryDAO.getFilteredRepositoryList(21)
            assertEquals(filter.size, 1)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testInsertRepositoryEntitiesShouldFailed() {
        val entities = mutableListOf(repositoryEntity)
        CoroutineScope(Dispatchers.IO).launch {
            repositoryDAO.insertRepositoryList(entities)
            val filter = repositoryDAO.getFilteredRepositoryList(21)
            assertNotEquals(filter.size, 3)
        }
    }

    @After
    fun tearDown() {
        repositoryDB.close()
    }
}