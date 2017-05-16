package com.felipecosta.kotlinrxjavasample.data

import android.content.Context
import io.reactivex.Observable
import io.reactivex.Observable.*
import io.reactivex.functions.BiFunction
import java.util.*

class FavoriteRepository(private val localStorage: LocalStorage) {

    constructor(context: Context, characterId: Int) : this(object : LocalStorage {
        override var storageValue: String
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
            set(value) {}
    })

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

    fun saveFavorite(characterId: Int = 0) {
        zip<Int, MutableList<Int>, List<Int>>(
                fromCallable { characterId },
                fetchFavorites().map { it.toMutableList() },
                BiFunction { t1, t2 -> t2.apply { add(t1) }.toList() })
                .subscribe { localStorage.storageValue = Arrays.toString(it.toIntArray()) }
    }

    fun removeFavorite(characterId: Int = 0) {
        fetchFavorites().flatMap { fromIterable(it) }
                .filter { it != characterId }
                .toList()
                .toObservable()
                .subscribe { localStorage.storageValue = it.toString() }
    }

}