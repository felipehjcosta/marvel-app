package com.github.felipehjcosta.marvelapp.cache

import android.arch.persistence.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.felipehjcosta.marvelapp.cache.data.CharacterRelations
import com.github.felipehjcosta.marvelapp.cache.data.CharactersDao
import com.github.felipehjcosta.marvelapp.cache.data.SummaryEntity
import com.github.felipehjcosta.marvelapp.cache.data.UrlEntity
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class CacheDatabaseTest {

    private val cacheDatabase: CacheDatabase = inMemoryDatabaseBuilder(getApplicationContext(),
            CacheDatabase::class.java).build()
    private val charactersDao: CharactersDao = cacheDatabase.charactersDao()

    @After
    fun tearDown() {
        cacheDatabase.close()
    }

    @Test
    fun ensureEmptyCharacterEntityByIdWhenDataDoesNotExist() {

        val itemsObserver = TestObserver.create<CharacterRelations>()

        charactersDao.findById(42L).subscribe(itemsObserver)

        itemsObserver.await(500L, TimeUnit.MILLISECONDS)
        itemsObserver.assertNoValues()
    }

    @Test
    fun ensureFindCharacterEntityByIdWhenDataExists() {

        val characterRelations = createFixture()

        charactersDao.insert(characterRelations)

        val itemsObserver = TestObserver.create<CharacterRelations>()

        charactersDao.findById(42L).subscribe(itemsObserver)

        itemsObserver.await(500L, TimeUnit.MILLISECONDS)
        itemsObserver.assertValue { it == characterRelations }
    }

    @Test
    fun ensureEmptyCharacterEntitiesWhenDataDoesNotExist() {

        val itemsObserver = TestObserver.create<List<CharacterRelations>>()

        charactersDao.all().subscribe(itemsObserver)

        itemsObserver.await(500L, TimeUnit.MILLISECONDS)
        itemsObserver.assertValue { it == emptyList<List<CharacterRelations>>() }
    }

    @Test
    fun ensureFindAllCharacterEntitiesWhenDataExists() {

        val characterRelations = createFixture()

        charactersDao.insert(characterRelations)

        val itemsObserver = TestObserver.create<List<CharacterRelations>>()

        charactersDao.all().subscribe(itemsObserver)

        itemsObserver.await(500L, TimeUnit.MILLISECONDS)
        itemsObserver.assertValue { it[0] == characterRelations }
    }

    private fun createFixture(): CharacterRelations {
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