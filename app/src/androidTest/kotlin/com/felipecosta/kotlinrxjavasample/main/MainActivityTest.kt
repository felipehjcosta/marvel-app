package com.felipecosta.kotlinrxjavasample.main

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.felipecosta.kotlinrxjavasample.MockDemoApplication
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.listing.view.CharacterItemRecyclerViewAdapter
import io.reactivex.Observable
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Inject
    lateinit var dataRepository: DataRepository

    @Before
    fun setUp() {
        val mockDemoApplication = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MockDemoApplication

        mockDemoApplication.applicationComponent.inject(this)
        Mockito.reset(dataRepository)
    }

    @Test
    fun whenMainActivityFirstAppearsThenFirstListItemIsShowing() {
        val characterName = "Spider-Man"
        val character = Character()
        character.name = characterName

        Mockito.`when`(dataRepository.getCharacterList(anyInt(), anyInt())).
                thenReturn(Observable.just(listOf(character)))

        activityRule.launchActivity(Intent())

        onView(withId(R.id.recycler_view)).
                perform(actionOnItem<CharacterItemRecyclerViewAdapter.ViewHolder>(hasDescendant(withText("Spider-Man")),
                        scrollTo())).
                check(matches(isDisplayed()))
    }

    @Test
    fun whenSelectSample2ThenSecondSampleIsShowing() {
        activityRule.launchActivity(Intent())

        onView(allOf(withText("Sample 2"), isDescendantOfA(withId(R.id.nav_view)), isCompletelyDisplayed())).
                perform(click())

        onView(allOf(withText("Sample 2"), isDescendantOfA(withId(R.id.main_content)))).
                check(matches(isDisplayed()))
    }
}