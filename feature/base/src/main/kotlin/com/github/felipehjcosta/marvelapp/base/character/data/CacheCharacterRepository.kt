package com.github.felipehjcosta.marvelapp.base.character.data

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.*
import com.github.felipehjcosta.marvelapp.cache.data.*
import io.reactivex.Single
import io.reactivex.Single.defer
import java.io.IOException


class CacheCharacterRepository(
    private val characterRepository: CharacterRepository,
    private val charactersDao: CharactersDao
) : CharacterRepository {

    override fun getCharacterList(offset: Int, limit: Int): Single<List<Character>> {
        return characterRepository.getCharacterList(offset, limit)
            .doOnSuccess { list -> list.forEach { saveInCache(it) } }
            .onErrorResumeNext { throwable: Throwable ->
                when (throwable) {
                    is IOException -> defer { readFromCache() }.flatMap {
                        if (it.isEmpty()) Single.error(throwable) else Single.just(it)
                    }
                    else -> Single.error(throwable)
                }
            }
    }

    override fun getCharacter(characterId: Int): Single<Character> {
        val memoryObservable = charactersDao.findById(characterId.toLong()).map { it.toCharacter() }

        val networkObservable = defer { characterRepository.getCharacter(characterId) }
            .doOnSuccess { saveInCache(it) }

        return memoryObservable.switchIfEmpty(networkObservable)
    }

    private fun saveInCache(character: Character) {
        charactersDao.insert(character.toCharacterRelations())
    }

    private fun readFromCache(): Single<List<Character>> {
        return charactersDao.all().map { list -> list.map { it.toCharacter() } }
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
                        items = comicListSummary.map {
                            Summary(
                                name = it.name,
                                resourceURI = it.resourceURI,
                                type = it.type
                            )
                        }
                    )
                },
                stories = storyListRelations.run {
                    StoryList(
                        available = storyListEntity.available,
                        returned = storyListEntity.returned,
                        collectionURI = storyListEntity.collectionURI,
                        items = storyListSummary.map {
                            Summary(
                                name = it.name,
                                resourceURI = it.resourceURI,
                                type = it.type
                            )
                        }
                    )
                },
                events = eventListRelations.run {
                    EventList(
                        available = eventListEntity.available,
                        returned = eventListEntity.returned,
                        collectionURI = eventListEntity.collectionURI,
                        items = eventListSummary.map {
                            Summary(
                                name = it.name,
                                resourceURI = it.resourceURI,
                                type = it.type
                            )
                        }
                    )
                },
                series = seriesListRelations.run {
                    SeriesList(
                        available = seriesListEntity.available,
                        returned = seriesListEntity.returned,
                        collectionURI = seriesListEntity.collectionURI,
                        items = seriesListSummary.map {
                            Summary(
                                name = it.name,
                                resourceURI = it.resourceURI,
                                type = it.type
                            )
                        }
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
                    comicListSummary = items.map { summary ->
                        SummaryEntity(
                            name = summary.name,
                            resourceURI = summary.resourceURI,
                            type = summary.type
                        )
                    }
                )
            },
            storyListRelations = stories.run {
                StoryListRelations(
                    storyListEntity = StoryListEntity(
                        available = available,
                        returned = returned,
                        collectionURI = collectionURI
                    ),
                    storyListSummary = items.map { summary ->
                        SummaryEntity(
                            name = summary.name,
                            resourceURI = summary.resourceURI,
                            type = summary.type
                        )
                    }
                )
            },
            eventListRelations = events.run {
                EventListRelations(
                    eventListEntity = EventListEntity(
                        available = available,
                        returned = returned,
                        collectionURI = collectionURI
                    ),
                    eventListSummary = items.map { summary ->
                        SummaryEntity(
                            name = summary.name,
                            resourceURI = summary.resourceURI,
                            type = summary.type
                        )
                    }
                )
            },
            seriesListRelations = series.run {
                SeriesListRelations(
                    seriesListEntity = SeriesListEntity(
                        available = available,
                        returned = returned,
                        collectionURI = collectionURI
                    ),
                    seriesListSummary = items.map { summary ->
                        SummaryEntity(
                            name = summary.name,
                            resourceURI = summary.resourceURI,
                            type = summary.type
                        )
                    }
                )
            }
        )
    }

}
