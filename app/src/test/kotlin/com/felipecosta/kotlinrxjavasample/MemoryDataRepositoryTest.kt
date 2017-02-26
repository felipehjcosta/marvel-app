package com.felipecosta.kotlinrxjavasample

import android.support.v4.util.LruCache
import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.utils.mock
import com.felipecosta.kotlinrxjavasample.utils.whenever
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class MemoryDataRepositoryTest {

    val dataRepository: DataRepository = mock()

    val memoryCache: LruCache<Int, Character> = mock()

    val character: Character = mock()

    lateinit var memoryDataRepository: MemoryDataRepository

    @Before
    fun setUp() {
        memoryDataRepository = MemoryDataRepository(dataRepository, memoryCache)
    }

    @Test
    fun whenGetCharacterListThenAssertItsCached() {
        whenever(character.id).thenReturn(42)

        whenever(dataRepository.getCharacterList(0, 1)).thenReturn(just(listOf(character)))

        val characterListObserver = TestObserver.create<List<Character>>()

        memoryDataRepository.getCharacterList(0, 1).subscribe(characterListObserver)

        verify(memoryCache).put(eq(42), eq(character))

        characterListObserver.dispose()
    }

    @Test
    fun givenCachedWhenGetCharacterThenReturnsCached() {
        whenever(character.id).thenReturn(42)

        whenever(memoryCache.get(42)).thenReturn(character)

        val characterObserver = TestObserver.create<Character>()

        memoryDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.assertValue { it == character }

        characterObserver.dispose()
    }

    @Test
    fun givenCachedWhenGetCharacterThenNotInteractWithNetwork() {
        whenever(character.id).thenReturn(42)

        whenever(memoryCache.get(42)).thenReturn(character)

        val characterObserver = TestObserver.create<Character>()

        memoryDataRepository.getCharacter(42).subscribe(characterObserver)

        verifyZeroInteractions(dataRepository)

        characterObserver.dispose()
    }

    @Test
    fun givenNotCachedWhenGetCharacterThenReturnsANewCharacter() {
        whenever(character.id).thenReturn(42)

        whenever(dataRepository.getCharacter(42)).thenReturn(just(character))

        val characterObserver = TestObserver.create<Character>()

        memoryDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.assertValue { it == character }

        characterObserver.dispose()
    }

    @Test
    fun whenDisposeFromGetCharacterListThenClearCache() {
        whenever(character.id).thenReturn(42)

        whenever(dataRepository.getCharacterList(0, 1)).thenReturn(just(listOf(character)))

        val characterListObserver = TestObserver.create<List<Character>>()

        memoryDataRepository.getCharacterList(0, 1).subscribe(characterListObserver)

        characterListObserver.dispose()

        verify(memoryCache).evictAll()
    }

    @Test
    fun whenDisposeFromGetCharacterThenClearCache() {
        whenever(dataRepository.getCharacter(42)).thenReturn(just(character))

        val characterObserver = TestObserver.create<Character>()

        memoryDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.dispose()

        verify(memoryCache).evictAll()
    }
}