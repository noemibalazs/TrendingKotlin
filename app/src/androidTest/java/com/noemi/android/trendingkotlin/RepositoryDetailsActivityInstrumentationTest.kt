package com.noemi.android.trendingkotlin

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.noemi.android.trendingkotlin.api.model.RepositoryDetails
import com.noemi.android.trendingkotlin.api.model.RepositoryReadMe
import com.noemi.android.trendingkotlin.details.RepositoryDetailsActivity
import com.noemi.android.trendingkotlin.util.displayData
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RepositoryDetailsActivityInstrumentationTest {

    private lateinit var context: Context

    @get:Rule
    val rule = activityScenarioRule<RepositoryDetailsActivity>()

    @Test
    fun testBusinessLogic() {

        val repositoryDetails = RepositoryDetails(
            id = 12, name = "Anko", description = "That's all", size = 33, forksCount = 12,
            stargazersCount = 21, openIssuesCount = 27, updated = "2021-07-18T12:12:12Z"
        )
        val repositoryReadMe = RepositoryReadMe(
            "Try to decode me... using Base64."
        )

        rule.scenario.onActivity {
            context = it.applicationContext

            it.findViewById<AppCompatTextView>(R.id.tvRepositoryName).text = repositoryDetails.name
            it.findViewById<AppCompatTextView>(R.id.tvRepositoryDescription).text = String.format(
                it.getString(R.string.txt_description),
                repositoryDetails.description
            )
            it.findViewById<AppCompatTextView>(R.id.tvRepositorySize).text = String.format(
                it.getString(R.string.txt_size),
                repositoryDetails.size
            )
            it.findViewById<AppCompatTextView>(R.id.tvRepositoryForks).text = String.format(
                it.getString(R.string.txt_forks),
                repositoryDetails.forksCount
            )
            it.findViewById<AppCompatTextView>(R.id.tvRepositoryWatchers).text = String.format(
                it.getString(R.string.txt_watchers),
                repositoryDetails.stargazersCount
            )
            it.findViewById<AppCompatTextView>(R.id.tvRepositoryIssues).text = String.format(
                it.getString(R.string.txt_open_issues),
                repositoryDetails.openIssuesCount
            )
            it.findViewById<AppCompatTextView>(R.id.tvRepositoryUpdated).text =
                repositoryDetails.updated.displayData()
            it.findViewById<AppCompatTextView>(R.id.tvReadmeContent).text = repositoryReadMe.content
        }

        onView(withId(R.id.tvRepositoryName)).check(matches(isDisplayed()))
            .check(matches(withText(repositoryDetails.name)))

        onView(withId(R.id.tvRepositoryDescription)).check(matches(isDisplayed()))
            .check(
                matches(
                    withText(
                        String.format(
                            context.getString(R.string.txt_description),
                            repositoryDetails.description
                        )
                    )
                )
            )
        onView(withId(R.id.tvRepositorySize)).check(matches(isDisplayed()))
            .check(
                matches(
                    withText(
                        String.format(
                            context.getString(R.string.txt_size),
                            repositoryDetails.size
                        )
                    )
                )
            )

        onView(withId(R.id.tvRepositoryForks)).check(matches(isDisplayed()))
            .check(
                matches(
                    withText(
                        String.format(
                            context.getString(R.string.txt_forks),
                            repositoryDetails.forksCount
                        )
                    )
                )
            )

        onView(withId(R.id.tvRepositoryIssues)).check(matches(isDisplayed()))
            .check(
                matches(
                    withText(
                        String.format(
                            context.getString(R.string.txt_open_issues),
                            repositoryDetails.openIssuesCount
                        )
                    )
                )
            )

        onView(withId(R.id.tvRepositorySize)).check(matches(isDisplayed()))
            .check(
                matches(
                    withText(
                        String.format(
                            context.getString(R.string.txt_size),
                            repositoryDetails.size
                        )
                    )
                )
            )

        onView(withId(R.id.tvRepositoryWatchers)).check(matches(isDisplayed()))
            .check(
                matches(
                    withText(
                        String.format(
                            context.getString(R.string.txt_watchers),
                            repositoryDetails.stargazersCount
                        )
                    )
                )
            )

        onView(withId(R.id.tvRepositoryUpdated)).check(matches(isDisplayed()))
            .check(
                matches(
                    withText(
                        repositoryDetails.updated.displayData()
                    )
                )
            )

        onView(withId(R.id.tvReadmeContent)).check(matches(isDisplayed()))
            .check(
                matches(
                    withText(
                        repositoryReadMe.content
                    )
                )
            )
    }
}