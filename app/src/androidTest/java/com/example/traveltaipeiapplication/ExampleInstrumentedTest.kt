package com.example.traveltaipeiapplication

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.traveltaipeiapplication.ui.AttractionWebActivity
import com.example.traveltaipeiapplication.ui.HomeActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.hamcrest.core.AllOf.allOf

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val activityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testHome_News() {
        Intents.init()

        waitViewWithId(R.id.recycler_view)
        onView(allOf(withId(R.id.recycler_view), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)

        intended(hasComponent(AttractionWebActivity::class.java.name))
        onView(withId(R.id.webView)).check(matches(isDisplayed()))

        Intents.release()
    }

    @Test
    fun testHome_Attraction() {
        Intents.init()

        waitViewWithId(R.id.recycler_view)
        onView(withId(R.id.viewpager))
                .perform(swipeLeft())

        Thread.sleep(1000)

        onView(allOf(withId(R.id.recycler_view), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)

        intended(hasComponent(AttractionWebActivity::class.java.name))
        onView(withId(R.id.tv_open_time)).check(matches(isDisplayed()))

        Intents.release()
    }

    fun waitViewWithId(id : Int) {
        var s = false
        var c = 0
        do {
            try {
                onView(allOf(withId(id), isDisplayed()))
                        .check(matches(isDisplayed()))
                s = true
            } catch (e: Throwable) {
                c++
                Thread.sleep(100)
            }
        } while (!s && c < 60)
        assertEquals(true, s)
    }
}