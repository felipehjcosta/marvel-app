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
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
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

        RxJavaPlugins.reset()
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

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

        onView(withId(R.id.listing_recycler_view)).
                perform(actionOnItem<CharacterItemRecyclerViewAdapter.ViewHolder>(hasDescendant(withText("Spider-Man")),
                        scrollTo())).
                check(matches(isDisplayed()))
    }

    @Test
    fun whenSelectSecondOptionThenSecondSampleIsShowing() {
        activityRule.launchActivity(Intent())

        onView(allOf(withText("Favorites"), isDescendantOfA(withId(R.id.nav_view)), isCompletelyDisplayed())).
                perform(click())

        onView(allOf(withText("Favorites"), isDescendantOfA(withId(R.id.main_content)))).
                check(matches(isDisplayed()))
    }
}