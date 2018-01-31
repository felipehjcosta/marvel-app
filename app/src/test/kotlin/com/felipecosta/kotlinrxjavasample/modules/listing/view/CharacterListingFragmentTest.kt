package com.felipecosta.kotlinrxjavasample.modules.listing.view

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.TestStubApplication
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterListViewModel
import com.felipecosta.kotlinrxjavasample.util.bindView
import com.felipecosta.rxaction.RxCommand
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject.createDefault
import io.reactivex.subjects.CompletableSubject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(application = TestStubApplication::class)
class CharacterListingFragmentTest {

    private val mockViewModel = mockk<CharacterListViewModel>(relaxed = true)

    private val mockLoadItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

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

        val recyclerView: RecyclerView by fragment.bindView(R.id.listing_recycler_view)
        assertEquals(1, recyclerView.adapter.itemCount)
    }

    @Test
    fun givenShowLoadingEmittedWhenStartFragmentItShouldShowSwipeRefresh() {
        createDefault(items).apply {
            every { mockViewModel.showLoading } returns Observable.just(true)
        }

        startFragment(fragment)

        val swipeRefresh: SwipeRefreshLayout by fragment.bindView(R.id.listing_swipe_refresh)

        assertTrue { swipeRefresh.isRefreshing }
    }

    @Test
    fun whenStartFragmentItShouldExecuteLoadItemsCommand() {
        val commandExecuteCompletable = CompletableSubject.create().apply {
            every { mockLoadItemsCommand.execute(any()) } returns this
        }
        every { mockViewModel.loadItemsCommand } returns mockLoadItemsCommand

        startFragment(fragment)

        assertTrue { commandExecuteCompletable.hasObservers() }
    }
}