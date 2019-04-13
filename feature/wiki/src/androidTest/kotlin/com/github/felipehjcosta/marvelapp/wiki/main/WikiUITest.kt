package com.github.felipehjcosta.marvelapp.wiki.main

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.felipehjcosta.marvelapp.wiki.MockMarvelAppApplication
import com.github.felipehjcosta.marvelapp.wiki.R
import com.github.felipehjcosta.marvelapp.wiki.utils.readAsset
import com.github.felipehjcosta.marvelapp.wiki.utils.withRecyclerView
import com.github.felipehjcosta.marvelapp.wiki.view.MainActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class WikiUITest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Inject
    lateinit var mockWebServer: MockWebServer

    init {
        val mockDemoApplication = ApplicationProvider.getApplicationContext<MockMarvelAppApplication>()

        mockDemoApplication.testApplicationComponent.inject(this)
    }

    @Before
    fun setUp() {

        mockWebServer.setDispatcher(object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse {
                println(">>> dispatch request: ${request!!.requestUrl.encodedPath()}")
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
                    "/v1/public/characters/1009351" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009351.json"))
                    "/v1/public/characters/1009610" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009610.json"))
                    "/v1/public/characters/1009718" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009718.json"))
                    "/v1/public/characters/1009368" -> MockResponse()
                            .setResponseCode(200)
                            .addHeader("Content-Type", "application/json")
                            .setBody(readAsset("1009368.json"))
                    else -> MockResponse().setResponseCode(404)
                }
            }
        })

        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun whenLaunchedThenTheFirstHighlightedCharacterItemIsShown() {
        onView(withRecyclerView(R.id.highlighted_characters_recycler_view).atPosition(0))
                .check(matches(hasDescendant(withText("Hulk"))))
                .check(matches(isDisplayed()))
    }

    @Test
    fun whenLaunchedThenTheFirstOtherCharacterItemIsShown() {
        onView(withRecyclerView(R.id.others_characters_recycler_view).atPosition(0))
                .check(matches(hasDescendant(withText("Thor"))))
                .check(matches(isDisplayed()))
    }
}