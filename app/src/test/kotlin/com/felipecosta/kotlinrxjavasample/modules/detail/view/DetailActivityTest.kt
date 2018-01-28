package com.felipecosta.kotlinrxjavasample.modules.detail.view

import android.support.annotation.IdRes
import android.support.design.widget.CollapsingToolbarLayout
import android.view.View
import android.widget.TextView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.detail.presentation.CharacterDetailViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable.just
import io.reactivex.subjects.BehaviorSubject.createDefault
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@RunWith(RobolectricTestRunner::class)
class DetailActivityTest {

    private val name = "Thor"
    private val description = "Description"
    private val comicsCount = 1
    private val eventsCount = 1
    private val seriesCount = 1
    private val storiesCount = 1

    private val mockViewModel = mockk<CharacterDetailViewModel>(relaxed = true)

    private val detailActivityController: ActivityController<DetailActivity> = Robolectric
            .buildActivity(DetailActivity::class.java)
            .create()
            .start()
            .postCreate(null)
            .apply { get().viewModel = mockViewModel }

    @Test
    fun givenSubscribedToDetailViewModelWhenResumeItShouldDisplayCharacterInformation() {

        every { mockViewModel.name } returns just(name)
        every { mockViewModel.description } returns just(description)
        every { mockViewModel.comicsCount } returns just(comicsCount)
        every { mockViewModel.eventsCount } returns just(eventsCount)
        every { mockViewModel.seriesCount } returns just(seriesCount)
        every { mockViewModel.storiesCount } returns just(storiesCount)

        detailActivityController.resume().visible()

        assertEquals(name, detailActivityController
                .findView<CollapsingToolbarLayout>(R.id.toolbar_layout)
                .title)

        assertEquals(description, detailActivityController
                .findView<TextView>(R.id.text_description)
                .text)

        assertEquals(comicsCount.toString(), detailActivityController
                .findView<TextView>(R.id.statistic_comics)
                .text)

        assertEquals(eventsCount.toString(), detailActivityController
                .findView<TextView>(R.id.statistic_events)
                .text)

        assertEquals(seriesCount.toString(), detailActivityController
                .findView<TextView>(R.id.statistic_series)
                .text)

        assertEquals(storiesCount.toString(), detailActivityController
                .findView<TextView>(R.id.statistic_stories)
                .text)

        verify { mockViewModel.characterCommand }
    }

    @Test
    fun givenResumedWhenPauseItShouldDisposeDetailViewModelSubscriptions() {
        val nameSubject = createDefault(name).apply {
            every { mockViewModel.name } returns this
        }
        val descriptionSubject = createDefault(description).apply {
            every { mockViewModel.description } returns this
        }
        val comicsCountSubject = createDefault(comicsCount).apply {
            every { mockViewModel.comicsCount } returns this
        }
        val eventsCountSubject = createDefault(eventsCount).apply {
            every { mockViewModel.eventsCount } returns this
        }
        val seriesCountSubject = createDefault(seriesCount).apply {
            every { mockViewModel.seriesCount } returns this
        }
        val storiesCountSubject = createDefault(storiesCount).apply {
            every { mockViewModel.storiesCount } returns this
        }

        detailActivityController.resume().visible()

        assertTrue { nameSubject.hasObservers() }
        assertTrue { descriptionSubject.hasObservers() }
        assertTrue { comicsCountSubject.hasObservers() }
        assertTrue { eventsCountSubject.hasObservers() }
        assertTrue { seriesCountSubject.hasObservers() }
        assertTrue { storiesCountSubject.hasObservers() }

        detailActivityController.pause()

        assertFalse { nameSubject.hasObservers() }
        assertFalse { descriptionSubject.hasObservers() }
        assertFalse { comicsCountSubject.hasObservers() }
        assertFalse { eventsCountSubject.hasObservers() }
        assertFalse { seriesCountSubject.hasObservers() }
        assertFalse { storiesCountSubject.hasObservers() }
    }

    inline fun <reified T : View> ActivityController<*>.findView(@IdRes idRes: Int): T {
        return get().findViewById<T>(idRes)
    }
}