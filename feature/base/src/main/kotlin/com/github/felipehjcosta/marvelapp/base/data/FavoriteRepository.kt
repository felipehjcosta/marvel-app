package com.github.felipehjcosta.marvelapp.base.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observable.*
import io.reactivex.functions.BiFunction
import java.util.*

class FavoriteRepository(private val localStorage: LocalStorage) {

    fun fetchFavorites(): Observable<List<Int>> {
        return fromCallable { localStorage.storageValue }
                .map { it.replace("[", "").replace("]", "") }
                .filter(String::isNotBlank)
                .map { it.split(",") }
                .flatMap { fromIterable(it) }
                .map(String::trim)
                .map(String::toInt)
                .toList()
                .toObservable()
    }

    fun isFavorite(characterId: Int = 0): Observable<Boolean> {
        return fetchFavorites()
                .map { it.contains(characterId) }
    }

    fun saveFavorite(characterId: Int = 0): Completable {
        return zip<Int, MutableList<Int>, List<Int>>(
                fromCallable { characterId },
                fetchFavorites().map { it.toMutableList() },
                BiFunction { newValue, favorites -> favorites.apply { add(newValue) } })
                .doOnNext { localStorage.storageValue = Arrays.toString(it.toIntArray()) }
                .ignoreElements()
    }

    fun removeFavorite(characterId: Int = 0): Completable {
        return fetchFavorites().flatMap { fromIterable(it) }
                .filter { it != characterId }
                .toList()
                .doOnSuccess { localStorage.storageValue = it.toString() }
                .toCompletable()
    }

}