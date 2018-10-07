package com.github.felipehjcosta.marvelapp.base.character.data

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import com.github.felipehjcosta.marvelapp.base.readResourceFile
import com.github.felipehjcosta.marvelapp.network.utils.createNetworkConverterFactory
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class NetworkDataRepositoryTest {

    private val mockWebServer = MockWebServer()

    private val baseUrl: String = "http://${mockWebServer.hostName}:${mockWebServer.port}"

    private val httpClient: OkHttpClient = OkHttpClient.Builder().build()
    private val service: CharacterService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(createNetworkConverterFactory())
            .client(httpClient)
            .build().create(CharacterService::class.java)

    private val networkDataRepository = NetworkDataRepository(service)

    @After
    fun tearDown() = mockWebServer.shutdown()

    @Test
    fun whenGetCharacterShouldDeliverCharacterObjectOnSuccess() {

        val body = readResourceFile("integrationTest/spider-man.json")

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(body))

        val itemObserver = TestObserver.create<Character>()
        networkDataRepository.getCharacter(1009664).subscribe(itemObserver)

        itemObserver.awaitTerminalEvent()

        itemObserver.assertValue { it.id == 1009664 }

        itemObserver.dispose()
    }

    @Test
    fun whenGetCharacterListShouldDeliverCharacterObjectsOnSuccess() {

        val body = readResourceFile("integrationTest/characters.json")

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(body))

        val itemObserver = TestObserver.create<List<Character>>()
        networkDataRepository.getCharacterList(0, 2).subscribe(itemObserver)

        itemObserver.awaitTerminalEvent()

        itemObserver.assertValue { it.size == 2 }

        itemObserver.dispose()
    }
}