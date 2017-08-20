package com.felipecosta.kotlinrxjavasample.data

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class NetworkDataRepositoryTest {

    val mockWebServer = MockWebServer()

    val baseUrl: String = "http://${mockWebServer.hostName}:${mockWebServer.port}"

    val httpClient: OkHttpClient = OkHttpClient.Builder().build()
    val service: CharacterService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build().create(CharacterService::class.java)

    val networkDataRepository = NetworkDataRepository(service)

    @After
    fun tearDown() = mockWebServer.shutdown()


    @Test
    fun whenGetCharacterShouldDeliverCharacterObjectOnSuccess() {

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

    @Test
    fun whenGetCharacterListShouldDeliverCharacterObjectsOnSuccess() {

        val body = javaClass.classLoader.getResourceAsStream("integrationTest/characters.json")?.use {
            it.reader().readText()
        }

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(body))

        val itemObserver = TestObserver.create<List<Character>>()
        networkDataRepository.getCharacterList(0, 2).subscribe(itemObserver)

        itemObserver.awaitTerminalEvent()

        itemObserver.assertValue { it.size == 2 }

        itemObserver.dispose()
    }
}