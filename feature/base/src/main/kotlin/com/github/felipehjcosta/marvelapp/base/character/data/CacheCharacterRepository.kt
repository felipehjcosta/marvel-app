package com.github.felipehjcosta.marvelapp.base.character.data

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.*
import com.github.felipehjcosta.marvelapp.cache.data.*
import io.reactivex.Observable
import io.reactivex.Observable.defer
import java.io.IOException


class CacheCharacterRepository(
        private val characterRepository: CharacterRepository,
        private val charactersDao: CharactersDao
) : CharacterRepository {

    override fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>> {
        return characterRepository.getCharacterList(offset, limit)
                .doOnNext { list -> list.forEach { saveInCache(it) } }
                .onErrorResumeNext { throwable: Throwable ->
                    when (throwable) {
                        is IOException -> Observable.defer { readFromCache() }.flatMap {
                            if (it.isEmpty()) Observable.error(throwable) else Observable.just(it)
                        }
                        else -> Observable.error(throwable)
                    }
                }
    }

    override fun getCharacter(characterId: Int): Observable<Character> {
        val memoryObservable = charactersDao.findById(characterId.toLong()).map { it.toCharacter() }

        val networkObservable = defer { characterRepository.getCharacter(characterId) }
                .doOnNext { saveInCache(it) }
                .singleOrError()

        return memoryObservable.switchIfEmpty(networkObservable).toObservable()
    }

    private fun saveInCache(character: Character) {
        charactersDao.insert(character.toCharacterRelations())
    }

    private fun readFromCache(): Observable<List<Character>> {
        return charactersDao.all().toObservable().map { list -> list.map { it.toCharacter() } }
    }

    private fun CharacterRelations.toCharacter(): Character {
        return character.run {
            Character(
                    id = id,
                    name = name,
                    description = description,
                    resourceURI = resourceURI,
                    urls = urls.map { Url(url = it.url, type = it.type) },
                    thumbnail = thumbnail.run { Thumbnail(path = path, extension = extension) },
                    comics = comicListRelations.run {
                        ComicList(
                                available = comicList.available,
                                returned = comicList.returned,
                                collectionURI = comicList.collectionURI,
                                items = comicListSummary.map { Summary(name = it.name, resourceURI = it.resourceURI, type = it.type) }
                        )
                    },
                    stories = storyListRelations.run {
                        StoryList(
                                available = storyListEntity.available,
                                returned = storyListEntity.returned,
                                collectionURI = storyListEntity.collectionURI,
                                items = storyListSummary.map { Summary(name = it.name, resourceURI = it.resourceURI, type = it.type) }
                        )
                    },
                    events = eventListRelations.run {
                        EventList(
                                available = eventListEntity.available,
                                returned = eventListEntity.returned,
                                collectionURI = eventListEntity.collectionURI,
                                items = eventListSummary.map { Summary(name = it.name, resourceURI = it.resourceURI, type = it.type) }
                        )
                    },
                    series = seriesListRelations.run {
                        SeriesList(
                                available = seriesListEntity.available,
                                returned = seriesListEntity.returned,
                                collectionURI = seriesListEntity.collectionURI,
                                items = seriesListSummary.map { Summary(name = it.name, resourceURI = it.resourceURI, type = it.type) }
                        )
                    }
            )
        }
    }

    private fun Character.toCharacterRelations(): CharacterRelations {
        return CharacterRelations(
                character = CharacterEntity(
                        id = id,
                        name = name,
                        description = description,
                        resourceURI = resourceURI

                ),
                urls = urls.map { UrlEntity(url = it.url, type = it.type) },
                thumbnail = thumbnail.run { ThumbnailEntity(path = path, extension = extension) },
                comicListRelations = comics.run {
                    ComicListRelations(
                            comicList = ComicListEntity(
                                    available = available,
                                    returned = returned,
                                    collectionURI = collectionURI
                            ),
                            comicListSummary = items.map { SummaryEntity(name = it.name, resourceURI = it.resourceURI, type = it.type) }
                    )
                },
                storyListRelations = stories.run {
                    StoryListRelations(
                            storyListEntity = StoryListEntity(
                                    available = available,
                                    returned = returned,
                                    collectionURI = collectionURI
                            ),
                            storyListSummary = items.map { SummaryEntity(name = it.name, resourceURI = it.resourceURI, type = it.type) }
                    )
                },
                eventListRelations = events.run {
                    EventListRelations(
                            eventListEntity = EventListEntity(
                                    available = available,
                                    returned = returned,
                                    collectionURI = collectionURI
                            ),
                            eventListSummary = items.map { SummaryEntity(name = it.name, resourceURI = it.resourceURI, type = it.type) }
                    )
                },
                seriesListRelations = series.run {
                    SeriesListRelations(
                            seriesListEntity = SeriesListEntity(
                                    available = available,
                                    returned = returned,
                                    collectionURI = collectionURI
                            ),
                            seriesListSummary = items.map { SummaryEntity(name = it.name, resourceURI = it.resourceURI, type = it.type) }
                    )
                }
        )
    }

}