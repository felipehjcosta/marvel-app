package com.github.felipehjcosta.marvelapp.base

import com.github.felipehjcosta.marvelapp.base.character.data.CacheCharacterRepository
import com.github.felipehjcosta.marvelapp.base.character.data.CharacterRepository
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.*
import com.github.felipehjcosta.marvelapp.cache.data.CharacterRelations
import com.github.felipehjcosta.marvelapp.cache.data.CharactersDao
import com.github.felipehjcosta.marvelapp.cache.data.SummaryEntity
import com.github.felipehjcosta.marvelapp.cache.data.UrlEntity
import io.mockk.*
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.Single.error
import io.reactivex.Single.just
import io.reactivex.observers.TestObserver
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

class CacheCharacterRepositoryTest {

    private val dataRepository = mockk<CharacterRepository>()

    private val charactersDao = mockk<CharactersDao>(relaxed = true)

    private val character = createCharacter()

    private val characterEntity = createCharacterRelations()

    private var cacheDataRepository = CacheCharacterRepository(dataRepository, charactersDao)

    @Test
    fun whenGetCharacterListThenAssertItsCached() {
        every { dataRepository.getCharacterList(0, 1) } returns just(listOf(character))

        val slot = slot<CharacterRelations>()
        every { charactersDao.insert(capture(slot)) } just Runs

        val itemObserver = TestObserver.create<List<Character>>()

        cacheDataRepository.getCharacterList(0, 1).subscribe(itemObserver)

        itemObserver.dispose()

        val actualCharacterEntity = slot.captured
        assertEquals(characterEntity, actualCharacterEntity)
    }

    @Test
    fun givenDecoratedCharacterListFailedCachedWhenGetCharacterListThenReturnCache() {
        every { dataRepository.getCharacterList(0, 1) } returns error(IOException())

        every { charactersDao.all() } returns Single.just(listOf(characterEntity))

        val itemObserver = TestObserver.create<List<Character>>()

        cacheDataRepository.getCharacterList(0, 1).subscribe(itemObserver)

        itemObserver.dispose()

        itemObserver.assertValue { it == listOf(character) }
    }

    @Test
    fun givenDecoratedCharacterListFailedWithEmptyCachedWhenGetCharacterListThenReturnCache() {
        val exception = IOException()
        every { dataRepository.getCharacterList(0, 1) } returns error(exception)

        every { charactersDao.all() } returns Single.just(emptyList())

        val itemObserver = TestObserver.create<List<Character>>()

        cacheDataRepository.getCharacterList(0, 1).subscribe(itemObserver)

        itemObserver.dispose()

        itemObserver.assertError { it == exception }
    }

    @Test
    fun givenCachedWhenGetCharacterThenReturnsCached() {

        every { charactersDao.findById(42L) } returns Maybe.just(characterEntity)

        val characterObserver = TestObserver.create<Character>()

        cacheDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.dispose()

        characterObserver.assertValue { it == character }
    }

    @Test
    fun givenCachedWhenGetCharacterThenNotInteractWithNetwork() {
        every { charactersDao.findById(42L) } returns Maybe.just(characterEntity)

        val characterObserver = TestObserver.create<Character>()

        cacheDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.dispose()

        verify { dataRepository.getCharacter(42) wasNot Called }
    }

    @Test
    fun givenNotCachedWhenGetCharacterThenReturnsANewCharacter() {
        every { dataRepository.getCharacter(42) } returns just(character)
        every { charactersDao.findById(42L) } returns Maybe.empty()

        val characterObserver = TestObserver.create<Character>()

        cacheDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.dispose()

        characterObserver.assertValue { it == character }
    }

    @Test
    fun givenNotCachedWhenGetCharacterThenCacheResult() {
        every { charactersDao.findById(42L) } returns Maybe.empty()
        every { dataRepository.getCharacter(42) } returns just(character)
        val slot = slot<CharacterRelations>()
        every { charactersDao.insert(capture(slot)) } just Runs

        val characterObserver = TestObserver.create<Character>()

        cacheDataRepository.getCharacter(42).subscribe(characterObserver)

        characterObserver.dispose()

        val actualCharacterEntity = slot.captured
        assertEquals(characterEntity, actualCharacterEntity)
    }

    private fun createCharacter(): Character {
        return Character(
                id = 42L,
                name = "Iron Man",
                description = "Wounded, captured and forced to build a weapon by his enemies, billionaire industrialist Tony Stark instead created an advanced suit of armor to save his life and escape captivity. Now with a new outlook on life, Tony uses his money and intelligence to make the world a safer, better place as Iron Man.",
                resourceURI = "http://gateway.marvel.com/v1/public/characters/1009368",
                urls = listOf(
                        Url(
                                type = "detail",
                                url = "http://marvel.com/characters/29/iron_man?utm_campaign=apiRef&utm_source=9549585bdad28d04622dcd45ed0238aa"
                        )
                ),
                thumbnail = Thumbnail(
                        path = "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55",
                        extension = "jpg"
                ),
                comics = ComicList(
                        available = 2287,
                        collectionURI = "http://gateway.marvel.com/v1/public/characters/1009368/comics",
                        items = listOf(
                                Summary(
                                        resourceURI = "http://gateway.marvel.com/v1/public/comics/43495",
                                        name = "A+X (2012) #2"
                                ),
                                Summary(
                                        resourceURI = "http://gateway.marvel.com/v1/public/comics/43506",
                                        name = "A+X (2012) #7"
                                )
                        )
                ),
                stories = StoryList(
                        available = 3256,
                        collectionURI = "http://gateway.marvel.com/v1/public/characters/1009368/stories",
                        items = listOf(
                                Summary(
                                        resourceURI = "http://gateway.marvel.com/v1/public/stories/670",
                                        name = "X-MEN (2004) #186",
                                        type = "cover"
                                ),
                                Summary(
                                        resourceURI = "http://gateway.marvel.com/v1/public/stories/892",
                                        name = "Cover #892",
                                        type = "cover"
                                )
                        )
                ),
                events = EventList(
                        available = 29,
                        collectionURI = "http://gateway.marvel.com/v1/public/characters/1009368/events",
                        items = listOf(
                                Summary(
                                        resourceURI = "http://gateway.marvel.com/v1/public/events/116",
                                        name = "Acts of Vengeance!"
                                ),
                                Summary(
                                        resourceURI = "http://gateway.marvel.com/v1/public/events/303",
                                        name = "Age of X"
                                )
                        )
                ),
                series = SeriesList(
                        available = 543,
                        collectionURI = "http://gateway.marvel.com/v1/public/characters/1009368/series",
                        items = listOf(
                                Summary(
                                        resourceURI = "http://gateway.marvel.com/v1/public/series/16450",
                                        name = "A+X (2012 - Present)"
                                ),
                                Summary(
                                        resourceURI = "http://gateway.marvel.com/v1/public/series/7524",
                                        name = "Adam: Legend of the Blue Marvel (2008)"
                                )
                        )
                )

        )
    }

    private fun createCharacterRelations(): CharacterRelations {
        return CharacterRelations().apply {
            character.apply {
                id = 42L
                name = "Iron Man"
                description = "Wounded, captured and forced to build a weapon by his enemies, billionaire industrialist Tony Stark instead created an advanced suit of armor to save his life and escape captivity. Now with a new outlook on life, Tony uses his money and intelligence to make the world a safer, better place as Iron Man."
                resourceURI = "http://gateway.marvel.com/v1/public/characters/1009368"
            }

            urls = listOf(
                    UrlEntity(
                            type = "detail",
                            url = "http://marvel.com/characters/29/iron_man?utm_campaign=apiRef&utm_source=9549585bdad28d04622dcd45ed0238aa"
                    )
            )

            thumbnail.apply {
                path = "http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55"
                extension = "jpg"
            }

            comicListRelations.apply {

                comicList.apply {
                    available = 2287
                    collectionURI = "http://gateway.marvel.com/v1/public/characters/1009368/comics"
                }

                comicListSummary = listOf(
                        SummaryEntity(
                                resourceURI = "http://gateway.marvel.com/v1/public/comics/43495",
                                name = "A+X (2012) #2"
                        ),
                        SummaryEntity(
                                resourceURI = "http://gateway.marvel.com/v1/public/comics/43506",
                                name = "A+X (2012) #7"
                        )
                )

            }

            storyListRelations.apply {

                storyListEntity.apply {
                    available = 3256
                    collectionURI = "http://gateway.marvel.com/v1/public/characters/1009368/stories"
                }
                storyListSummary = listOf(
                        SummaryEntity(
                                resourceURI = "http://gateway.marvel.com/v1/public/stories/670",
                                name = "X-MEN (2004) #186",
                                type = "cover"
                        ),
                        SummaryEntity(
                                resourceURI = "http://gateway.marvel.com/v1/public/stories/892",
                                name = "Cover #892",
                                type = "cover"
                        )
                )
            }

            eventListRelations.apply {

                eventListEntity.apply {
                    available = 29
                    collectionURI = "http://gateway.marvel.com/v1/public/characters/1009368/events"
                }
                eventListSummary = listOf(
                        SummaryEntity(
                                resourceURI = "http://gateway.marvel.com/v1/public/events/116",
                                name = "Acts of Vengeance!"
                        ),
                        SummaryEntity(
                                resourceURI = "http://gateway.marvel.com/v1/public/events/303",
                                name = "Age of X"
                        )
                )
            }

            seriesListRelations.apply {

                seriesListEntity.apply {
                    available = 543
                    collectionURI = "http://gateway.marvel.com/v1/public/characters/1009368/series"
                }
                seriesListSummary = listOf(
                        SummaryEntity(
                                resourceURI = "http://gateway.marvel.com/v1/public/series/16450",
                                name = "A+X (2012 - Present)"
                        ),
                        SummaryEntity(
                                resourceURI = "http://gateway.marvel.com/v1/public/series/7524",
                                name = "Adam: Legend of the Blue Marvel (2008)"
                        )
                )
            }

        }
    }
}