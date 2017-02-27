package com.felipecosta.kotlinrxjavasample.data

import android.support.v4.util.LruCache
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.rx.set
import com.jakewharton.rxrelay2.BehaviorRelay.*
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class CacheDataRepository(private val dataRepository: DataRepository,
                          private val memoryLruCache: LruCache<Int, Character>) : DataRepository {

    private val lruCacheObservable = createDefault<LruCache<Int, Character>>(memoryLruCache)
            .doOnDispose { memoryLruCache.evictAll() }
            .replay(1)
            .refCount()

    override fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>> {
        return combineLatest(dataRepository.getCharacterList(offset, limit), lruCacheObservable,
                BiFunction { characterList, cache ->
                    characterList.apply { forEach { cache[it.id] = it } }
                })
    }

    override fun getCharacter(characterId: Int): Observable<Character> {
        return lruCacheObservable.flatMap { cache ->
            val cachedCharacter = cache.get(characterId)
            if (cachedCharacter != null) {
                just(cachedCharacter)
            } else {
                combineLatest(just(cache), defer { dataRepository.getCharacter(characterId) },
                        BiFunction { cache: LruCache<Int, Character>, character: Character ->
                            cache[character.id] = character
                            character
                        })
            }
        }
    }

    override fun favoriteCharacter(characterId: Int): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}