package com.github.felipehjcosta.marvelapp.cache

import io.reactivex.Completable
import io.reactivex.Observable

interface Cache<Key : Any, Value : Any> {
    fun get(key: Key): Observable<Value>
    fun set(key: Key, value: Value): Completable
}
