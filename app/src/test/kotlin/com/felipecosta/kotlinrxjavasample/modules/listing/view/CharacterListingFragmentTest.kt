package com.felipecosta.kotlinrxjavasample.modules.listing.view

import android.support.v7.widget.RecyclerView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterListViewModel
import com.felipecosta.kotlinrxjavasample.util.bindView
import io.mockk.every
import io.mockk.mockk
import io.reactivex.subjects.BehaviorSubject.createDefault
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(application = TestStubApplication::class)
class CharacterListingFragmentTest {

    private val mockViewModel = mockk<CharacterListViewModel>(relaxed = true)

    private val items = listOf(CharacterItemViewModel(
            1,
            "Thor",
            "http://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg"))

    @Test
    fun givenItemsEmittedWhenStartFragmentItShouldPopulateTheList() {
        val fragment = CharacterListingFragment()
        fragment.viewModel = mockViewModel
        createDefault(items).apply {
            every { mockViewModel.items } returns this
        }

        startFragment(fragment)

        val recyclerView: RecyclerView by fragment.bindView(R.id.listing_recycler_view)
        assertEquals(1, recyclerView.adapter.itemCount)
    }
}