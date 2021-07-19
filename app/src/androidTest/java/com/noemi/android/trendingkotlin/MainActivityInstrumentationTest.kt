package com.noemi.android.trendingkotlin

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.noemi.android.trendingkotlin.adapter.*
import com.noemi.android.trendingkotlin.api.model.Repository
import com.noemi.android.trendingkotlin.ui.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityInstrumentationTest {

    @get:Rule
    val rule = activityScenarioRule<MainActivity>()

    @Test
    fun testAppContext() {
        val context = InstrumentationRegistry.getInstrumentation().context
        Assert.assertEquals("com.noemi.android.trendingkotlin.test", context.packageName)
    }

    @Test
    fun testBusinessLogic() {
        val repository1 = Repository(
            id = 21,
            name = "kotlinx.coroutines",
            fullName = "kotlin/coroutines",
            description = "Library support for Kotlin coroutines",
            updated = "2021-07-21T17:12:23Z",
            stargazersCount = 21
        )
        val repository2 = Repository(
            id = 12,
            name = "kotlinx-kotlin",
            fullName = "kotlin/coroutines",
            description = "Library support for Kotlin coroutines",
            updated = "2021-06-21T17:12:23Z",
            stargazersCount = 12
        )
        val repos = mutableListOf(repository1, repository2)
        val repositoryClickListener: RepositoryClickListener = { }
        val adapter = RepositoryAdapter(repositoryClickListener)
        adapter.submitList(repos)

        rule.scenario.onActivity {
            it.findViewById<RecyclerView>(R.id.rvRepositories).adapter = adapter
        }

        onView(withId(R.id.etRepositoryStars)).check(matches(isDisplayed()))
        onView(withId(R.id.ivDone)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.clClear)).check(matches(not(isDisplayed())))

        onView(withId(R.id.rvRepositories)).check(matches(isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RepositoryVH>(
                0,
                ViewActions.click()
            )
        )
    }
}