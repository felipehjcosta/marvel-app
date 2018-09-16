package com.github.felipehjcosta.marvelapp.base

import com.github.felipehjcosta.marvelapp.base.data.CacheDataRepository
import com.github.felipehjcosta.marvelapp.base.data.DataRepository
import com.github.felipehjcosta.marvelapp.base.data.SimpleDiskCache
import com.github.felipehjcosta.marvelapp.base.data.pojo.Character
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import kotlinx.serialization.json.JSON
import org.junit.Test
import org.mockito.Mockito
import java.io.InputStream
import kotlin.test.assertEquals

class CacheDataRepositoryTest {

    private val dataRepository: DataRepository = mock()

    private val cache: SimpleDiskCache = mock()

    private val entry: SimpleDiskCache.InputStreamEntry = mock()

    private val character = Character().apply { id = 42 }

    private var cacheDataRepository = CacheDataRepository(dataRepository, cache)

    @Test
    fun whenGetCharacterListThenAssertItsCached() {
        character.id = 42

        whenever(dataRepository.getCharacterList(0, 1)).thenReturn(just(listOf(character)))

        val characterListObserver = TestObserver.create<List<Character>>()

        cacheDataRepository.getCharacterList(0, 1).subscribe(characterListObserver)

        characterListObserver.dispose()

        val argumentCaptor = argumentCaptor<InputStream>()

        Mockito.verify(cache).put(eq("42"), argumentCaptor.capture())

        assertEquals(JSON.stringify(character), argumentCaptor.lastValue.use { it.reader().readText() })
    }

    @Test
    fun givenCachedWhenGetCharacterThenReturnsCached() {
        whenever(entry.inputStream).thenReturn(JSON.stringify(character).byteInputStream())

        whenever(cache.getInputStream("42")).thenReturn(entry)

        whenever(cache.contains("42")).thenReturn(true)

        val characterObserver = TestObserver.create<Character>()

        cacheDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.dispose()

        characterObserver.assertValue { it == character }
    }

    @Test
    fun givenCachedWhenGetCharacterThenNotInteractWithNetwork() {
        whenever(entry.inputStream).thenReturn(JSON.stringify(character).byteInputStream())

        whenever(cache.getInputStream("42")).thenReturn(entry)

        whenever(cache.contains("42")).thenReturn(true)

        val characterObserver = TestObserver.create<Character>()

        cacheDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.dispose()

        verifyZeroInteractions(dataRepository)
    }

    @Test
    fun givenNotCachedWhenGetCharacterThenReturnsANewCharacter() {
        whenever(dataRepository.getCharacter(42)).thenReturn(just(character))

        val characterObserver = TestObserver.create<Character>()

        cacheDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.dispose()

        characterObserver.assertValue { it == character }
    }

    @Test
    fun givenNotCachedWhenGetCharacterThenCacheResult() {
        whenever(dataRepository.getCharacter(42)).thenReturn(just(character))

        val characterObserver = TestObserver.create<Character>()

        cacheDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.dispose()

        val argumentCaptor = argumentCaptor<InputStream>()

        Mockito.verify(cache).put(eq("42"), argumentCaptor.capture())

        assertEquals(JSON.stringify(character), argumentCaptor.lastValue.use { it.reader().readText() })
    }

}