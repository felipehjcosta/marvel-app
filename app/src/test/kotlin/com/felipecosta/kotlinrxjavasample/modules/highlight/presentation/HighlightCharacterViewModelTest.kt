package com.felipecosta.kotlinrxjavasample.modules.highlight.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.highlight.datamodel.HighlightedCharactersDataModel
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable.just
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class HighlightCharacterViewModelTest {

    val dataModel = mock<HighlightedCharactersDataModel>()

    val viewModel = HighlightCharacterViewModel(dataModel)

    @Before
    fun setUp() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }

        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun subscribedToItemsWhenExecuteLoadItemsCommandThenReturnItems() {

        val characterName = "Wolverine"
        val character = Character().apply { name = characterName }

        whenever(dataModel.getHighlightedCharacters()).thenReturn(just(listOf(character)))

        val itemsObserver = TestObserver.create<List<CharacterItemViewModel>>()

        viewModel.items.subscribe(itemsObserver)

        val disposable = viewModel.loadItemsCommand.execute().subscribe()

        itemsObserver.assertValue { it[0].name == characterName }

        disposable.dispose()
    }
}