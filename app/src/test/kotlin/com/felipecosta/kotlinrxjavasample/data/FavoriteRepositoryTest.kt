package com.felipecosta.kotlinrxjavasample.data

import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.TestObserver
import org.junit.Test

class FavoriteRepositoryTest {

    val localStorage: LocalStorage = mock()

    val favoriteRepository: FavoriteRepository = FavoriteRepository(localStorage)

    @Test
    fun givenEmptyStorageValueWhenFetchFavoritesItShouldEnsureEmptyList() {
        whenever(localStorage.storageValue).thenReturn("")

        val observer = TestObserver.create<List<Int>>()
        favoriteRepository.fetchFavorites().subscribe(observer)

        observer.assertValue(emptyList())
    }

    @Test
    fun givenSingleStorageValueWhenFetchFavoritesItShouldEnsureEmptyList() {
        whenever(localStorage.storageValue).thenReturn("[42]")

        val observer = TestObserver.create<List<Int>>()
        favoriteRepository.fetchFavorites().subscribe(observer)

        observer.assertValue(listOf(42))
    }

    @Test
    fun givenMultiplyStorageValueWhenFetchFavoritesItShouldEnsureEmptyList() {
        whenever(localStorage.storageValue).thenReturn("[42, 34]")

        val observer = TestObserver.create<List<Int>>()
        favoriteRepository.fetchFavorites().subscribe(observer)

        observer.assertValue(listOf(42, 34))
    }

    @Test
    fun givenCharacterIdWithEmptyStorageValueWhenIsFavoriteItShouldNotHaveIt() {
        whenever(localStorage.storageValue).thenReturn("")

        val observer = TestObserver.create<Boolean>()
        favoriteRepository.isFavorite(42).subscribe(observer)

        observer.assertValue(false)
    }

    @Test
    fun givenCharacterIdWithSingleStorageValueWhenIsFavoriteItShouldHaveIt() {
        whenever(localStorage.storageValue).thenReturn("[42]")

        val observer = TestObserver.create<Boolean>()
        favoriteRepository.isFavorite(42).subscribe(observer)

        observer.assertValue(true)
    }

    @Test
    fun givenCharacterIdWithEmptyStorageValueWhenSaveFavoriteItShouldAddIt () {
        whenever(localStorage.storageValue).thenReturn("")

        favoriteRepository.saveFavorite(42)

        verify(localStorage).storageValue = "[42]"
    }

    @Test
    fun givenCharacterIdWithSingleStorageValueWhenRemoveFavoriteItShouldRemoveIt() {
        whenever(localStorage.storageValue).thenReturn("[42]")

        favoriteRepository.removeFavorite(42)

        verify(localStorage).storageValue = "[]"
    }

    @Test
    fun givenCharacterIdWithEmptyStorageValueWhenRemoveFavoriteItShouldDoNothing() {
        whenever(localStorage.storageValue).thenReturn("")

        favoriteRepository.removeFavorite(42)

        verify(localStorage).storageValue = "[]"
    }
}