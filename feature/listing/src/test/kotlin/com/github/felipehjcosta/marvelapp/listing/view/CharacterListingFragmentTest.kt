package com.github.felipehjcosta.marvelapp.listing.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.felipecosta.rxaction.RxCommand
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.listing.R
import com.github.felipehjcosta.marvelapp.listing.presentation.CharacterItemViewModel
import com.github.felipehjcosta.marvelapp.listing.presentation.CharacterListViewModel
import com.github.felipehjcosta.marvelapp.listing.presentation.CharacterListViewModelInputOutput
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject.createDefault
import io.reactivex.subjects.CompletableSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class CharacterListingFragmentTest {

    private val mockViewModel = mockk<CharacterListViewModel>(relaxed = true).apply {
        every { input } returns mockk(relaxed = true)
        every { output } returns mockk(relaxed = true)
    }

    private val mockImageLoader = mockk<ImageLoader>(relaxed = true)

    private val items = listOf(
        CharacterItemViewModel(
            1,
            "Thor",
            "http://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg"
        )
    )

    private val fragment = CharacterListingFragment().apply {
        viewModel = mockViewModel
        imageLoader = mockImageLoader
    }

    @Before
    fun setUp() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }

        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun givenItemsEmittedWhenStartFragmentItShouldPopulateTheList() {
        createDefault(items).apply {
            every { mockViewModel.output.items } returns this
        }

        launchFragmentInContainer { fragment }.moveToState(Lifecycle.State.RESUMED).onFragment {
            val recyclerView: RecyclerView = it.view!!.findViewById(R.id.recycler_view)
            assertEquals(1, recyclerView.adapter!!.itemCount)
        }
    }

    @Test
    fun givenShowLoadingEmittedWhenStartFragmentItShouldShowSwipeRefresh() {
        createDefault(items).apply {
            every { mockViewModel.output.showLoading } returns Observable.just(true)
        }

        launchFragmentInContainer { fragment }.moveToState(Lifecycle.State.RESUMED).onFragment {
            val swipeRefresh: SwipeRefreshLayout = it.view!!.findViewById(R.id.swipe_refresh_view)

            assertTrue { swipeRefresh.isRefreshing }
        }
    }

    @Test
    fun whenStartFragmentItShouldExecuteLoadItemsCommand() {
        val mockLoadItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val commandExecuteCompletable = CompletableSubject.create().apply {
            every { mockLoadItemsCommand.execute(any()) } returns this
        }
        every { mockViewModel.input.loadItemsCommand } returns mockLoadItemsCommand

        launchFragmentInContainer { fragment }.moveToState(Lifecycle.State.RESUMED)

        assertTrue { commandExecuteCompletable.hasObservers() }
    }

    @Test
    fun givenItemsWhenDispatchScrollEventItShouldLoadMoreItems() {
        createDefault(generateItems()).apply {
            every { mockViewModel.output.items } returns this
        }

        every { mockViewModel.output.showLoading } returns Observable.just(false)

        val mockLoadMoreItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val commandExecuteCompletable = CompletableSubject.create().apply {
            every { mockLoadMoreItemsCommand.execute(any()) } returns this
        }
        every { mockViewModel.input.loadMoreItemsCommand } returns mockLoadMoreItemsCommand

        every { mockViewModel.output.showLoadingMore } returns Observable.just(false)

        launchFragmentInContainer { fragment }.moveToState(Lifecycle.State.RESUMED)
        assertFalse { commandExecuteCompletable.hasObservers() }

        val recyclerView: RecyclerView = fragment.view!!.findViewById(R.id.recycler_view)
        recyclerView.scrollBy(0, 2000)

        assertTrue { commandExecuteCompletable.hasObservers() }
    }

    @Test
    fun `When go through Fragment lifecycle it should bind view model properties followed by unbind them`() {
        val itemsSubject = createDefault(generateItems()).apply {
            every { mockViewModel.output.items } returns this
        }

        val showLoadingSubject = createDefault(true).apply {
            every { mockViewModel.output.showLoading } returns this
        }

        val mockLoadItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val loadItemsCommandExecuteCompletable = CompletableSubject.create().apply {
            every { mockLoadItemsCommand.execute(any()) } returns this
        }
        every { mockViewModel.input.loadItemsCommand } returns mockLoadItemsCommand

        val newItemsSubject = createDefault(generateItems()).apply {
            every { mockViewModel.output.newItems } returns this
        }

        val fragmentScenario =
            launchFragmentInContainer { fragment }.moveToState(Lifecycle.State.RESUMED)

        assertTrue { itemsSubject.hasObservers() }
        assertTrue { showLoadingSubject.hasObservers() }
        assertTrue { loadItemsCommandExecuteCompletable.hasObservers() }
        assertTrue { newItemsSubject.hasObservers() }

        fragmentScenario.moveToState(Lifecycle.State.DESTROYED)

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