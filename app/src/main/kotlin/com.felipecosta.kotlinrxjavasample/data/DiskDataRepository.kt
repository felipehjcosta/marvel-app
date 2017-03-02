package com.felipecosta.kotlinrxjavasample.data

import android.content.Context
import com.felipecosta.kotlinrxjavasample.R
import io.reactivex.Observable
import io.reactivex.Observable.*
import io.reactivex.functions.BiFunction
import java.util.*

class DiskDataRepository(context: Context, val characterId: Int) : PreferencesRepository(context) {

    val favoriteKey = context.getString(R.string.saved_favorite_characters)!!

    fun fetchFavorites(): Observable<List<Int>> {
        return just(get(favoriteKey))
                .map { it.replace("[", "").replace("]", "") }
                .filter(String::isNotBlank)
                .map { it.split(",") }
                .flatMap { fromIterable(it) }
                .map(String::trim)
                .map(String::toInt)
                .toList()
                .toObservable()
    }

    fun isFavorite(): Observable<Boolean> {
        return fetchFavorites()
                .map { it.contains(characterId) }
    }

    fun saveFavorite() {
        zip<Int, MutableList<Int>, List<Int>>(
                just(characterId),
                fetchFavorites().map { it.toMutableList() },
                BiFunction { t1, t2 -> t2.apply { add(t1) }.toList() })
                .subscribe { put(favoriteKey, Arrays.toString(it.toIntArray())) }
    }

    fun removeFavorite() {
        fetchFavorites().flatMap { fromIterable(it) }
                .filter { it != characterId }
                .toList()
                .toObservable()
                .subscribe { put(favoriteKey, it.toString()) }
    }

}