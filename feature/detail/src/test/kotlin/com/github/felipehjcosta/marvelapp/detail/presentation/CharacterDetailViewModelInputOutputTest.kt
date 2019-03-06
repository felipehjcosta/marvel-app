package com.github.felipehjcosta.marvelapp.detail.presentation

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.*
import com.github.felipehjcosta.marvelapp.detail.datamodel.DetailDataModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

internal class CharacterDetailViewModelInputOutputTest {

    private val characterId = 1

    private val dataModel = mockk<DetailDataModel>()

    private val viewModel = CharacterDetailViewModelInputOutput(characterId, dataModel)

    @Before
    fun setUp() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }

        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun subscribedToPropertiesWhenExecuteLoadCharacterCommandThenReturnItems() {

        val expectedName = "Wolverine"
        val expectedDescription = "Description"
        val expectedThumbnailUrl = "https://thumbnailUrl.jpg"
        val expectedComicsCount = 1
        val expectedEventsCount = 1
        val expectedSeriesCount = 1
        val expectedStoriesCount = 1
        val character = Character().apply {
            name = expectedName
            description = expectedDescription
            thumbnail = Thumbnail().apply {
                path = "http://thumbnailUrl"
                extension = "jpg"
            }
            comics = ComicList().apply { items = listOf(Summary()) }
            events = EventList().apply { items = listOf(Summary()) }
            series = SeriesList().apply { items = listOf(Summary()) }
            stories = StoryList().apply { items = listOf(Summary()) }
        }

        every { dataModel.character(characterId) } returns just(character)

        val nameObservable = subscribeTo { viewModel.output.name }
        val descriptionObservable = subscribeTo { viewModel.output.description }
        val thumbnailUrlObservable = subscribeTo { viewModel.output.thumbnailUrl }
        val comicsCountObservable = subscribeTo { viewModel.output.comicsCount }
        val eventsCountObservable = subscribeTo { viewModel.output.eventsCount }
        val seriesCountObservable = subscribeTo { viewModel.output.seriesCount }
        val storiesCountObservable = subscribeTo { viewModel.output.storiesCount }

        viewModel.input.characterCommand.execute().subscribe()

        nameObservable.await(500L, TimeUnit.MILLISECONDS)
        nameObservable.assertValue(expectedName)
        descriptionObservable.assertValue(expectedDescription)
        thumbnailUrlObservable.assertValue(expectedThumbnailUrl)
        comicsCountObservable.assertValue(expectedComicsCount)
        eventsCountObservable.assertValue(expectedEventsCount)
        seriesCountObservable.assertValue(expectedSeriesCount)
        storiesCountObservable.assertValue(expectedStoriesCount)
    }

    private inline fun <reified T> subscribeTo(block: () -> Observable<T>): TestObserver<T> =
            TestObserver.create<T>().apply {
                block().subscribe(this)
            }

}