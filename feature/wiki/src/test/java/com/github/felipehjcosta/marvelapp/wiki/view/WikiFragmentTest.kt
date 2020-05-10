package com.github.felipehjcosta.marvelapp.wiki.view

import androidx.recyclerview.widget.RecyclerView
import com.felipecosta.rxaction.RxCommand
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.test.TestStubApplication
import com.github.felipehjcosta.marvelapp.wiki.R
import com.github.felipehjcosta.marvelapp.wiki.SupportFragmentController
import com.github.felipehjcosta.marvelapp.wiki.presentation.CharacterItemViewModel
import com.github.felipehjcosta.marvelapp.wiki.presentation.HighlightedCharactersViewModel
import com.github.felipehjcosta.marvelapp.wiki.presentation.OthersCharactersViewModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.CompletableSubject
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
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
class WikiFragmentTest {

    private val mockHighlightedCharactersViewModel =
        mockk<HighlightedCharactersViewModel>(relaxed = true).apply {
            every { input } returns mockk(relaxed = true)
            every { output } returns mockk(relaxed = true)
        }

    private val mockOthersCharactersViewModel =
        mockk<OthersCharactersViewModel>(relaxed = true).apply {
            every { input } returns mockk(relaxed = true)
            every { output } returns mockk(relaxed = true)
        }

    private val mockImageLoader = mockk<ImageLoader>(relaxed = true)

    private val fragment = WikiFragment().apply {
        highlightedCharactersViewModel = mockHighlightedCharactersViewModel
        othersCharactersViewModel = mockOthersCharactersViewModel
        imageLoader = mockImageLoader
    }

    @Test
    fun `given highlighted characters emitted when start Fragment it should populate gallery`() {
        BehaviorSubject.createDefault(generateItems()).apply {
            every { mockHighlightedCharactersViewModel.output.items } returns this
        }

        SupportFragmentController.setupFragment(fragment)

        val recyclerView: RecyclerView =
            fragment.view!!.findViewById(R.id.highlighted_characters_recycler_view)
        assertEquals(5, recyclerView.adapter!!.itemCount)
    }

    @Test
    fun `given others characters emitted when start Fragment it should populate list`() {
        BehaviorSubject.createDefault(generateItems()).apply {
            every { mockOthersCharactersViewModel.output.items } returns this
        }

        SupportFragmentController.setupFragment(fragment)

        val recyclerView: RecyclerView =
            fragment.view!!.findViewById(R.id.others_characters_recycler_view)
        assertEquals(5, recyclerView.adapter!!.itemCount)
    }

    @Test
    fun `When go through Fragment lifecycle it should bind view models properties followed by unbind them`() {

        val highlightedItemsSubject = BehaviorSubject.createDefault(generateItems()).apply {
            every { mockHighlightedCharactersViewModel.output.items } returns this
        }

        val othersItemsSubject = BehaviorSubject.createDefault(generateItems()).apply {
            every { mockOthersCharactersViewModel.output.items } returns this
        }

        val mockHighlightedLoadItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val highlightedLoadItemsCommandExecuteCompletable = CompletableSubject.create().apply {
            every { mockHighlightedLoadItemsCommand.execute(any()) } returns this
        }
        every { mockHighlightedCharactersViewModel.input.loadItemsCommand } returns mockHighlightedLoadItemsCommand

        val mockOthersLoadItemsCommand = mockk<RxCommand<Any>>(relaxed = true)

        val othersLoadItemsCommandExecuteCompletable = CompletableSubject.create().apply {
            every { mockOthersLoadItemsCommand.execute(any()) } returns this
        }
        every { mockOthersCharactersViewModel.input.loadItemsCommand } returns mockOthersLoadItemsCommand

        val controller = SupportFragmentController.of(fragment).create(null).start().resume()

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