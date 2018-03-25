package com.github.felipehjcosta.marvelapp.base.data

import io.mockk.Runs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.observers.TestObserver
import org.junit.Test

class FavoriteRepositoryTest {

    private val localStorage = mockk<LocalStorage>()

    private val favoriteRepository = FavoriteRepository(localStorage)

    @Test
    fun givenEmptyStorageValueWhenFetchFavoritesItShouldEnsureEmptyList() {
        every { localStorage.storageValue } returns ""

        TestObserver.create<List<Int>>().apply {
            favoriteRepository.fetchFavorites().subscribe(this)
            assertValue(emptyList())
        }

    }

    @Test
    fun givenSingleStorageValueWhenFetchFavoritesItShouldEnsureEmptyList() {
        every { localStorage.storageValue } returns "42"

        TestObserver.create<List<Int>>().apply {
            favoriteRepository.fetchFavorites().subscribe(this)
            assertNoErrors()
            assertValue(listOf(42))
        }
    }

    @Test
    fun givenMultiplyStorageValueWhenFetchFavoritesItShouldEnsureEmptyList() {
        every { localStorage.storageValue } returns "[42, 34]"

        TestObserver.create<List<Int>>().apply {
            favoriteRepository.fetchFavorites().subscribe(this)
            assertNoErrors()
            assertValue(listOf(42, 34))
        }
    }

    @Test
    fun givenCharacterIdWithEmptyStorageValueWhenIsFavoriteItShouldNotHaveIt() {
        every { localStorage.storageValue } returns ""

        TestObserver.create<Boolean>().apply {
            favoriteRepository.isFavorite(42).subscribe(this)
            assertNoErrors()
            assertValue(false)
        }
    }

    @Test
    fun givenCharacterIdWithSingleStorageValueWhenIsFavoriteItShouldHaveIt() {
        every { localStorage.storageValue } returns "[42]"

        TestObserver.create<Boolean>().apply {
            favoriteRepository.isFavorite(42).subscribe(this)
            assertNoErrors()
            assertValue(true)
        }
    }

    @Test
    fun givenCharacterIdWithEmptyStorageValueWhenSaveFavoriteItShouldAddIt() {
        every { localStorage.storageValue } returns ""
        every { localStorage.storageValue = "[42]" } just Runs

        TestObserver.create<Nothing>().apply {
            favoriteRepository.saveFavorite(42).subscribe(this)
            assertNoErrors()
        }

        verify { localStorage.storageValue = "[42]" }
    }

    @Test
    fun givenCharacterIdWithSingleStorageValueWhenRemoveFavoriteItShouldRemoveIt() {
        every { localStorage.storageValue } returns "[42]"
        every { localStorage.storageValue = "[]" } just Runs

        TestObserver.create<Nothing>().apply {
            favoriteRepository.removeFavorite(42).subscribe(this)
            assertNoErrors()
        }

        verify { localStorage.storageValue = "[]" }
    }

    @Test
    fun givenCharacterIdWithEmptyStorageValueWhenRemoveFavoriteItShouldDoNothing() {
        every { localStorage.storageValue } returns ""
        every { localStorage.storageValue = "[]" } just Runs

        TestObserver.create<Nothing>().apply {
            favoriteRepository.removeFavorite(42).subscribe(this)
            assertNoErrors()
        }

        verify { localStorage.storageValue = "[]" }
    }

    @Test
    fun whenRemoveFavoriteThrowsAnExceptionItShouldPropagate() {
        val expectedThrowable = Exception()
        every { localStorage.storageValue } answers { throw expectedThrowable }

        TestObserver.create<Nothing>().apply {
            favoriteRepository.removeFavorite(42).subscribe(this)
            assertNotComplete()
            assertError(expectedThrowable)
        }
    }

    @Test
    fun whenSaveFavoriteThrowsAnExceptionItShouldPropagate() {
        val expectedThrowable = Exception()
        every { localStorage.storageValue } answers { throw expectedThrowable }

        TestObserver.create<Nothing>().apply {
            favoriteRepository.saveFavorite(42).subscribe(this)
            assertNotComplete()
            assertError(expectedThrowable)
        }
    }
}