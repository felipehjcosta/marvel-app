package com.felipecosta.kotlinrxjavasample.modules.wiki.view

import android.support.v7.widget.RecyclerView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.TestStubApplication
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.felipecosta.kotlinrxjavasample.modules.wiki.presentation.HighlightedCharactersViewModel
import com.felipecosta.kotlinrxjavasample.modules.wiki.presentation.OthersCharactersViewModel
import com.felipecosta.kotlinrxjavasample.util.bindView
import com.felipecosta.rxaction.RxCommand
import io.mockk.every
import io.mockk.mockk
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.CompletableSubject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentController.of
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(application = TestStubApplication::class)
class WikiFragmentTest {

    private val mockHighlightedCharactersViewModel = mockk<HighlightedCharactersViewModel>(relaxed = true)

    private val mockOthersCharactersViewModel = mockk<OthersCharactersViewModel>(relaxed = true)

    private val fragment = WikiFragment().apply {
        highlightedCharactersViewModel = mockHighlightedCharactersViewModel
        othersCharactersViewModel = mockOthersCharactersViewModel
    }

    @Test
    fun `given highlighted characters emitted when start Fragment it should populate gallery`() {
        BehaviorSubject.createDefault(generateItems()).apply {
            every { mockHighlightedCharactersViewModel.items } returns this
        }

        startFragment(fragment)

        val recyclerView: RecyclerView by fragment.bindView(R.id.highlighted_characters_recycler_view)
        assertEquals(5, recyclerView.adapter.itemCount)
    }

    @Test
    fun `given others characters emitted when start Fragment it should populate list`() {
        BehaviorSubject.createDefault(generateItems()).apply {
            every { mockOthersCharactersViewModel.items } returns this
        }

        startFragment(fragment)

        val recyclerView: RecyclerView by fragment.bindView(R.id.others_characters_recycler_view)
        assertEquals(5, recyclerView.adapter.itemCount)
    }

    @Test
    fun `When go through Fragment lifecycle it should bind view models properties followed by unbind them`() {

        val highlightedItemsSubject = BehaviorSubject.createDefault(generateItems()).apply {
            every { mockHighlightedCharactersViewModel.items } returns this
        }

        val othersItemsSubject = BehaviorSubject.createDefault(generateItems()).apply {
            every { mockOthersCharactersViewModel.items } returns this
        }

        val mockHighlightedLoadItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val highlightedLoadItemsCommandExecuteCompletable = CompletableSubject.create().apply {
            every { mockHighlightedLoadItemsCommand.execute(any()) } returns this
        }
        every { mockHighlightedCharactersViewModel.loadItemsCommand } returns mockHighlightedLoadItemsCommand

        val mockOthersLoadItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val othersLoadItemsCommandExecuteCompletable = CompletableSubject.create().apply {
            every { mockOthersLoadItemsCommand.execute(any()) } returns this
        }
        every { mockOthersCharactersViewModel.loadItemsCommand } returns mockOthersLoadItemsCommand

        val controller = of(fragment).create(null).start().resume()

        assertTrue { highlightedItemsSubject.hasObservers() }
        assertTrue { othersItemsSubject.hasObservers() }
        assertTrue { highlightedLoadItemsCommandExecuteCompletable.hasObservers() }
        assertTrue { othersLoadItemsCommandExecuteCompletable.hasObservers() }

        controller.pause().stop().destroy()

        assertFalse { highlightedItemsSubject.hasObservers() }
        assertFalse { othersItemsSubject.hasObservers() }
        assertFalse { highlightedLoadItemsCommandExecuteCompletable.hasObservers() }
        assertFalse { othersLoadItemsCommandExecuteCompletable.hasObservers() }
    }

    private fun generateItems(): List<CharacterItemViewModel> {
        var count = 5

        return generateSequence { (count--).takeIf { it > 0 } }
                .map { mockk<CharacterItemViewModel>(relaxed = true) }
                .toList()
    }
}