package com.felipecosta.kotlinrxjavasample

import android.support.v4.util.LruCache
import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.rx.set
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Observable.*
import io.reactivex.functions.BiFunction

class MemoryDataRepository(private val dataRepository: DataRepository,
                           private val memoryLruCache: LruCache<Int, Character>) : DataRepository {

    private val characterListRequestRelay = PublishRelay.create<Pair<Int, Int>>()

    private val cacheRelay = BehaviorRelay.create<LruCache<Int, Character>>()

    private val characterListObservable: Observable<List<Character>>

    init {
        val networkObservable: Observable<List<Character>> = characterListRequestRelay
                .flatMap { dataRepository.getCharacterList(it.first, it.second) }

        characterListObservable = combineLatest(networkObservable, just(memoryLruCache),
                BiFunction { networkList: List<Character>, cache: LruCache<Int, Character> ->
                    networkList.apply { forEach { cache[it.id] = it } }
                })
                .doOnDispose { memoryLruCache.evictAll() }
                .share()
    }

    override fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>> {
        characterListRequestRelay.accept(offset to limit)
        return characterListObservable
    }

    override fun getCharacter(characterId: Int): Observable<Character> {
        val memoryObservable = defer {
            val cachedCharacter = memoryLruCache.get(characterId)
            if (cachedCharacter == null) {
                empty<Character>()
            } else {
                just(cachedCharacter)
            }
        }

        val networkObservable = defer { dataRepository.getCharacter(characterId) }
                .doOnNext({ memoryLruCache[it.id] = it })

        return concat(memoryObservable, networkObservable)
                .firstElement()
                .toObservable()
    }

    override fun favoriteCharacter(characterId: Int): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}