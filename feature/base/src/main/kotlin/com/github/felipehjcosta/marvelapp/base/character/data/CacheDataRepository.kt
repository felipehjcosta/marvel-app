package com.github.felipehjcosta.marvelapp.base.character.data

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import com.jakewharton.rxrelay2.BehaviorRelay.*
import io.reactivex.Observable
import kotlinx.serialization.json.JSON
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

    private fun saveInCache(character: Character) {
        cache.put(character.id.toString(), JSON.stringify(character).byteInputStream())
    }

    private fun readFromCache(inputStream: InputStream): Character {
        return JSON.parse(inputStream.reader().readText())
    }

}