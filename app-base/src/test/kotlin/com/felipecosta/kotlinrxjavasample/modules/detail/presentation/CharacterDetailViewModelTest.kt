package com.felipecosta.kotlinrxjavasample.modules.detail.presentation

import com.felipecosta.kotlinrxjavasample.data.FavoriteRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.*
import com.felipecosta.kotlinrxjavasample.modules.detail.datamodel.DetailDataModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CharacterDetailViewModelTest {

    private val characterId = 1

    private val dataModel = mockk<DetailDataModel>()

    private val favoriteRepository = mockk<FavoriteRepository>()

    private val viewModel = CharacterDetailViewModel(characterId, dataModel, favoriteRepository)

    @BeforeEach
    fun setUp() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }

        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    internal fun subscribedToPropertiesWhenExecuteLoadCharacterCommandThenReturnItems() {

        val expectedName = "Wolverine"
        val expectedDescription = "Description"
        val expectedThumbnailUrl = "http://thumbnailUrl.jpg"
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

        val nameObservable = subscribeTo { viewModel.name }
        val descriptionObservable = subscribeTo { viewModel.description }
        val thumbnailUrlObservable = subscribeTo { viewModel.thumbnailUrl }
        val comicsCountObservable = subscribeTo { viewModel.comicsCount }
        val eventsCountObservable = subscribeTo { viewModel.eventsCount }
        val seriesCountObservable = subscribeTo { viewModel.seriesCount }
        val storiesCountObservable = subscribeTo { viewModel.storiesCount }

        viewModel.characterCommand.execute().subscribe()

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