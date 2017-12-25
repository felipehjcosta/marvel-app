package com.felipecosta.kotlinrxjavasample.data

import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.observers.TestObserver
import org.junit.Test

class FavoriteRepositoryTest {

    private val localStorage: LocalStorage = mock()

    private val favoriteRepository: FavoriteRepository = FavoriteRepository(localStorage)

    @Test
    fun givenEmptyStorageValueWhenFetchFavoritesItShouldEnsureEmptyList() {
        whenever(localStorage.storageValue).thenReturn("")

        TestObserver.create<List<Int>>().apply {
            favoriteRepository.fetchFavorites().subscribe(this)
            assertValue(emptyList())
        }

    }

    @Test
    fun givenSingleStorageValueWhenFetchFavoritesItShouldEnsureEmptyList() {
        whenever(localStorage.storageValue).thenReturn("[42]")

        TestObserver.create<List<Int>>().apply {
            favoriteRepository.fetchFavorites().subscribe(this)
            assertNoErrors()
            assertValue(listOf(42))
        }
    }

    @Test
    fun givenMultiplyStorageValueWhenFetchFavoritesItShouldEnsureEmptyList() {
        whenever(localStorage.storageValue).thenReturn("[42, 34]")

        TestObserver.create<List<Int>>().apply {
            favoriteRepository.fetchFavorites().subscribe(this)
            assertNoErrors()
            assertValue(listOf(42, 34))
        }
    }

    @Test
    fun givenCharacterIdWithEmptyStorageValueWhenIsFavoriteItShouldNotHaveIt() {
        whenever(localStorage.storageValue).thenReturn("")

        TestObserver.create<Boolean>().apply {
            favoriteRepository.isFavorite(42).subscribe(this)
            assertNoErrors()
            assertValue(false)
        }
    }

    @Test
    fun givenCharacterIdWithSingleStorageValueWhenIsFavoriteItShouldHaveIt() {
        whenever(localStorage.storageValue).thenReturn("[42]")

        TestObserver.create<Boolean>().apply {
            favoriteRepository.isFavorite(42).subscribe(this)
            assertNoErrors()
            assertValue(true)
        }
    }

    @Test
    fun givenCharacterIdWithEmptyStorageValueWhenSaveFavoriteItShouldAddIt() {
        whenever(localStorage.storageValue).thenReturn("")

        TestObserver.create<Nothing>().apply {
            favoriteRepository.saveFavorite(42).subscribe(this)
            assertNoErrors()
        }

        verify(localStorage).storageValue = "[42]"
    }

    @Test
    fun givenCharacterIdWithSingleStorageValueWhenRemoveFavoriteItShouldRemoveIt() {
        whenever(localStorage.storageValue).thenReturn("[42]")

        TestObserver.create<Nothing>().apply {
            favoriteRepository.removeFavorite(42).subscribe(this)
            assertNoErrors()
        }

        verify(localStorage).storageValue = "[]"
    }

    @Test
    fun givenCharacterIdWithEmptyStorageValueWhenRemoveFavoriteItShouldDoNothing() {
        whenever(localStorage.storageValue).thenReturn("")

        TestObserver.create<Nothing>().apply {
            favoriteRepository.removeFavorite(42).subscribe(this)
            assertNoErrors()
        }

        verify(localStorage).storageValue = "[]"
    }

    @Test
    fun whenRemoveFavoriteThrowsAnExceptionItShouldPropagate() {
        val expectedThrowable = Exception()
        whenever(localStorage.storageValue).doAnswer { throw expectedThrowable }

        TestObserver.create<Nothing>().apply {
            favoriteRepository.removeFavorite(42).subscribe(this)
            assertNotComplete()
            assertError(expectedThrowable)
        }
    }

    @Test
    fun whenSaveFavoriteThrowsAnExceptionItShouldPropagate() {
        val expectedThrowable = Exception()
        whenever(localStorage.storageValue).doAnswer { throw expectedThrowable }

        TestObserver.create<Nothing>().apply {
            favoriteRepository.saveFavorite(42).subscribe(this)
            assertNotComplete()
            assertError(expectedThrowable)
        }
    }
}