package com.felipecosta.kotlinrxjavasample.data

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


class NetworkDataRepositoryTest {

    val mockWebServer = MockWebServer()

    val baseUrl: String = "http://${mockWebServer.hostName}:${mockWebServer.port}"

    val networkDataRepository = NetworkDataRepository(baseUrl)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun name() {

        val body = javaClass.classLoader.getResourceAsStream("integrationTest/spider-man.json")?.use {
            it.reader().readText()
        }

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(body))

        val itemObserver = TestObserver.create<Character>()
        networkDataRepository.getCharacter(1009664).subscribe(itemObserver)

        itemObserver.awaitTerminalEvent()

        itemObserver.assertValue { it.id == 1009664 }

        itemObserver.dispose()
    }
}