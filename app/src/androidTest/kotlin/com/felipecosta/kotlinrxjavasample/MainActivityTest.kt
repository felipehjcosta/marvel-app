package com.felipecosta.kotlinrxjavasample

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions.open
import android.support.test.espresso.contrib.DrawerMatchers.isClosed
import android.support.test.espresso.contrib.DrawerMatchers.isOpen
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.felipecosta.kotlinrxjavasample.listing.view.MyItemRecyclerViewAdapter
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun whenMainActivityFirstAppearsThenFirstListItemIsShowing() {
        onView(withId(R.id.recycler_view)).
                perform(actionOnItem<MyItemRecyclerViewAdapter.ViewHolder>(hasDescendant(withText("Item 1")),
                        scrollTo())).
                check(matches(isDisplayed()))
    }

    @Test
    fun whenSelectSample2ThenSecondSampleIsShowing() {

        onView(withId(R.id.drawer_layout)).perform(open())

        onView(withId(R.id.drawer_layout)).check(matches(isOpen()))

        onView(allOf(withText("Sample 2"), isDescendantOfA(withId(R.id.drawer_layout)))).
                perform(click())

        onView(withId(R.id.drawer_layout)).check(matches(isClosed()))

        onView(allOf(withText("Sample 2"), isDescendantOfA(withId(R.id.main_content)))).
                check(matches(isDisplayed()))
    }
}