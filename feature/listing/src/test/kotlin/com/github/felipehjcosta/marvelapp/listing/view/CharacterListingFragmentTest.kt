package com.github.felipehjcosta.marvelapp.listing.view

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.felipecosta.rxaction.RxCommand
import com.github.felipehjcosta.marvelapp.listing.R
import com.github.felipehjcosta.marvelapp.base.util.bindView
import com.github.felipehjcosta.marvelapp.listing.BuildConfig
import com.github.felipehjcosta.marvelapp.listing.TestStubApplication
import com.github.felipehjcosta.marvelapp.listing.presentation.CharacterItemViewModel
import com.github.felipehjcosta.marvelapp.listing.presentation.CharacterListViewModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject.createDefault
import io.reactivex.subjects.CompletableSubject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentController.of
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startVisibleFragment
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(
        application = TestStubApplication::class,
        manifest = Config.NONE,
        constants = BuildConfig::class,
        sdk = [21]
)
class CharacterListingFragmentTest {

    private val mockViewModel = mockk<CharacterListViewModel>(relaxed = true)

    private val items = listOf(CharacterItemViewModel(
            1,
            "Thor",
            "http://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg"))

    private val fragment = CharacterListingFragment().apply { viewModel = mockViewModel }

    @Test
    fun givenItemsEmittedWhenStartFragmentItShouldPopulateTheList() {
        createDefault(items).apply {
            every { mockViewModel.items } returns this
        }

        startFragment(fragment)

        val recyclerView: RecyclerView by fragment.bindView(R.id.recycler_view)
        assertEquals(1, recyclerView.adapter.itemCount)
    }

    @Test
    fun givenShowLoadingEmittedWhenStartFragmentItShouldShowSwipeRefresh() {
        createDefault(items).apply {
            every { mockViewModel.showLoading } returns Observable.just(true)
        }

        startFragment(fragment)

        val swipeRefresh: SwipeRefreshLayout by fragment.bindView(R.id.swipe_refresh_view)

        assertTrue { swipeRefresh.isRefreshing }
    }

    @Test
    fun whenStartFragmentItShouldExecuteLoadItemsCommand() {
        val mockLoadItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val commandExecuteCompletable = CompletableSubject.create().apply {
            every { mockLoadItemsCommand.execute(any()) } returns this
        }
        every { mockViewModel.loadItemsCommand } returns mockLoadItemsCommand

        startFragment(fragment)

        assertTrue { commandExecuteCompletable.hasObservers() }
    }

    @Test
    fun givenItemsWhenDispatchScrollEventItShouldLoadMoreItems() {
        createDefault(generateItems()).apply {
            every { mockViewModel.items } returns this
        }

        val mockLoadMoreItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val commandExecuteCompletable = CompletableSubject.create().apply {
            every { mockLoadMoreItemsCommand.execute(any()) } returns this
        }
        every { mockViewModel.loadMoreItemsCommand } returns mockLoadMoreItemsCommand

        every { mockViewModel.showLoadingMore } returns Observable.just(false)

        startVisibleFragment(fragment)

        assertFalse { commandExecuteCompletable.hasObservers() }

        val recyclerView: RecyclerView by fragment.bindView(R.id.recycler_view)
        recyclerView.scrollBy(0, 1000)

        Robolectric.getForegroundThreadScheduler().advanceBy(500L, TimeUnit.MILLISECONDS)
        Robolectric.flushForegroundThreadScheduler()
        assertTrue { commandExecuteCompletable.hasObservers() }
    }

    @Test
    fun `When go through Fragment lifecycle it should bind view model properties followed by unbind them`() {
        val itemsSubject = createDefault(generateItems()).apply {
            every { mockViewModel.items } returns this
        }

        val showLoadingSubject = createDefault(true).apply {
            every { mockViewModel.showLoading } returns this
        }

        val mockLoadItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val loadItemsCommandExecuteCompletable = CompletableSubject.create().apply {
            every { mockLoadItemsCommand.execute(any()) } returns this
        }
        every { mockViewModel.loadItemsCommand } returns mockLoadItemsCommand

        val newItemsSubject = createDefault(generateItems()).apply {
            every { mockViewModel.newItems } returns this
        }

        val controller = of(fragment).create(null).start().resume()

        assertTrue { itemsSubject.hasObservers() }
        assertTrue { showLoadingSubject.hasObservers() }
        assertTrue { loadItemsCommandExecuteCompletable.hasObservers() }
        assertTrue { newItemsSubject.hasObservers() }

        controller.pause().stop().destroy()

        assertFalse { itemsSubject.hasObservers() }
        assertFalse { showLoadingSubject.hasObservers() }
        assertFalse { loadItemsCommandExecuteCompletable.hasObservers() }
        assertFalse { newItemsSubject.hasObservers() }
    }

    private fun generateItems(): List<CharacterItemViewModel> {
        var count = 10

        return generateSequence { (count--).takeIf { it > 0 } }
                .map { mockk<CharacterItemViewModel>(relaxed = true) }
                .toList()
    }
}