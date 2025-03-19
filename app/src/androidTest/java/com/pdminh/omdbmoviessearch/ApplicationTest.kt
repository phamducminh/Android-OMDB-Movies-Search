package com.pdminh.omdbmoviessearch

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.pdminh.omdbmoviessearch.ui.MovieSearchActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ApplicationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun runApp() {
        ActivityScenario.launch(MovieSearchActivity::class.java)

        onView(withText("Search your favourite movie")).check(matches(isDisplayed()))
        onView(withId(R.id.search)).perform(click())
    }
}