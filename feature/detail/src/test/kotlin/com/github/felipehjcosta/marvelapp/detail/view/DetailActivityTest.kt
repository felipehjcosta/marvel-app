package com.github.felipehjcosta.marvelapp.detail.view

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import com.felipecosta.rxaction.RxCommand
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.detail.R
import com.github.felipehjcosta.marvelapp.detail.presentation.CharacterDetailViewModel
import com.github.felipehjcosta.marvelapp.test.TestStubApplication
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable.just
import io.reactivex.subjects.BehaviorSubject.createDefault
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@RunWith(RobolectricTestRunner::class)
@Config(
    application = TestStubApplication::class,
    manifest = Config.NONE,
    sdk = [21]
)
@Ignore
class DetailActivityTest {

    private val name = "Thor"
    private val description = "Description"
    private val comicsCount = 1
    private val eventsCount = 1
    private val seriesCount = 1
    private val storiesCount = 1

    private val mockViewModel = mockk<CharacterDetailViewModel>(relaxed = true).apply {
        every { input } returns mockk(relaxed = true)
        every { output } returns mockk(relaxed = true)
    }

    private val mockImageLoader = mockk<ImageLoader>(relaxed = true)

    private val detailActivityController: ActivityController<DetailActivity> = Robolectric
        .buildActivity(DetailActivity::class.java)
        .create()
        .start()
        .postCreate(null)
        .apply {
            get().apply {
                viewModel = mockViewModel
                imageLoader = mockImageLoader
            }
        }

    @Test
    fun givenSubscribedToDetailViewModelWhenResumeItShouldDisplayCharacterInformation() {

        val mockCharacterCommand = mockk<RxCommand<Any>>(relaxed = true)

        every { mockViewModel.input.characterCommand } returns mockCharacterCommand

        every { mockViewModel.output.name } returns just(name)
        every { mockViewModel.output.description } returns just(description)
        every { mockViewModel.output.comicsCount } returns just(comicsCount)
        every { mockViewModel.output.eventsCount } returns just(eventsCount)
        every { mockViewModel.output.seriesCount } returns just(seriesCount)
        every { mockViewModel.output.storiesCount } returns just(storiesCount)

        detailActivityController.resume().visible()

        assertEquals(
            name, detailActivityController
                .findView<CollapsingToolbarLayout>(R.id.toolbar_layout)
                .title
        )

        assertEquals(
            description, detailActivityController
                .findView<TextView>(R.id.text_description)
                .text
        )

        assertEquals(
            comicsCount.toString(), detailActivityController
                .findView<TextView>(R.id.statistic_comics)
                .text
        )

        assertEquals(
            eventsCount.toString(), detailActivityController
                .findView<TextView>(R.id.statistic_events)
                .text
        )

        assertEquals(
            seriesCount.toString(), detailActivityController
                .findView<TextView>(R.id.statistic_series)
                .text
        )

        assertEquals(
            storiesCount.toString(), detailActivityController
                .findView<TextView>(R.id.statistic_stories)
                .text
        )

        verify { mockCharacterCommand.execute(any()) }
    }

    @Test
    fun givenResumedWhenPauseItShouldDisposeDetailViewModelSubscriptions() {
        val nameSubject = createDefault(name).apply {
            every { mockViewModel.output.name } returns this
        }
        val descriptionSubject = createDefault(description).apply {
            every { mockViewModel.output.description } returns this
        }
        val comicsCountSubject = createDefault(comicsCount).apply {
            every { mockViewModel.output.comicsCount } returns this
        }
        val eventsCountSubject = createDefault(eventsCount).apply {
            every { mockViewModel.output.eventsCount } returns this
        }
        val seriesCountSubject = createDefault(seriesCount).apply {
            every { mockViewModel.output.seriesCount } returns this
        }
        val storiesCountSubject = createDefault(storiesCount).apply {
            every { mockViewModel.output.storiesCount } returns this
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