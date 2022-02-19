package com.sample.clean

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sample.clean.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RecyclerviewTest {

    /** Launches [MainActivity] for every test  */
    @Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    /**
     * Test a heading of the recycler view is clickable.
     */
    @Test
    fun testIsClickable() {
        Espresso.onView(withId(R.id.nav_host_container))
            .check(ViewAssertions.matches(ViewMatchers.isClickable()))
    }

    @Test
    fun clickItemOnSecondPos() {
        Espresso.onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition(2, ViewActions.click()))
    }

    @Test
    fun performClickOnFirstItem() {
        Espresso.onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()))
    }

    @Test
    fun performClickOnBasedOnText() {
        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItem(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("")), ViewActions.click()
                )
            )
    }

    @Test
    fun checkTheItemPositionContent() {
        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition(2, ViewActions.click())
            )
    }
}