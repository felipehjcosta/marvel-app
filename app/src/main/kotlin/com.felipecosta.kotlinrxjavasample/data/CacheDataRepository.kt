package com.felipecosta.kotlinrxjavasample.data

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.google.gson.Gson
import com.jakewharton.rxrelay2.BehaviorRelay.*
import io.reactivex.Observable
import java.io.InputStream


class CacheDataRepository(private val dataRepository: DataRepository,
                          private val cache: SimpleDiskCache) : DataRepository {

    override fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>> {
        return dataRepository.getCharacterList(offset, limit).
                doOnNext { list -> list.forEach { saveInCache(it) } }
    }

    override fun getCharacter(characterId: Int): Observable<Character> {
        val memoryObservable = just(cache)
                .flatMap {
                    if (it.contains(characterId.toString())) {
                        it.getInputStream(characterId.toString())!!.use {
                            just(readFromCache(it.inputStream))
                        }
                    } else {
                        empty<Character>()
                    }
                }

        val networkObservable = defer { dataRepository.getCharacter(characterId) }
                .doOnNext { saveInCache(it) }

        return concat(memoryObservable, networkObservable).take(1)
    }

    private fun saveInCache(character: Character) = Gson().apply { cache.put(character.id.toString(), toJson(character).byteInputStream()) }

    private fun readFromCache(inputStream: InputStream): Character = with(Gson()) { fromJson(inputStream.reader(), Character::class.java) }

    override fun favoriteCharacter(characterId: Int): Observable<Boolean> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}