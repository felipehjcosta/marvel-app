package com.felipecosta.kotlinrxjavasample.main

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.felipecosta.kotlinrxjavasample.MockDemoApplication
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.utils.RxEspressoScheduleHandler
import com.felipecosta.kotlinrxjavasample.utils.readAsset
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule @JvmField
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Inject
    lateinit var mockWebServer: MockWebServer

    val rxEspressoScheduleHandler = RxEspressoScheduleHandler()

    init {
        val mockDemoApplication = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MockDemoApplication

        mockDemoApplication.applicationComponent.inject(this)
    }

    @Before
    fun setUp() {

        mockWebServer.setDispatcher(object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse {
                return when (request!!.requestUrl.encodedPath()) {
                    "/v1/public/characters/1009664" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009664.json"))
                    "/v1/public/characters/1009220" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009220.json"))
                    "/v1/public/characters/1010733" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1010733.json"))
                    "/v1/public/characters/1009629" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009629.json"))
                    "/v1/public/characters/1009175" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009175.json"))
                    "/v1/public/characters/1009268" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009268.json"))
                    "/v1/public/characters/1009417" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009417.json"))
                    else -> MockResponse().setResponseCode(404)
                }
            }
        })

        RxJavaPlugins.setScheduleHandler(rxEspressoScheduleHandler)
        Espresso.registerIdlingResources(rxEspressoScheduleHandler.idlingResource)
    }

    @After
    fun tearDown() {
        Espresso.unregisterIdlingResources(rxEspressoScheduleHandler.idlingResource)
    }

    @Test
    fun whenMainActivityFirstAppearsThenFirstListItemIsShowing() {
        activityRule.launchActivity(Intent())
        onView(allOf(withId(R.id.others_characters_recycler_view), hasDescendant(withText("Thor"))))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
    }
}