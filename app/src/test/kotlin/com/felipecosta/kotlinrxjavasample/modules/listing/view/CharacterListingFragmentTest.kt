package com.felipecosta.kotlinrxjavasample.modules.listing.view

import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterListViewModel
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
    fun givenSubscribedToShowLoadingWhenStartFragmentItShouldShowLoading() {
        val fragment = CharacterListingFragment()
        fragment.viewModel = mockViewModel
        createDefault(items).apply {
            every { mockViewModel.items } returns this
        }

        startFragment(fragment)

        assertEquals(1, fragment.adapter.itemCount)
    }
}