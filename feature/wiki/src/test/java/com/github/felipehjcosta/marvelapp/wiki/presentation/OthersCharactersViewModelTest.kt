package com.github.felipehjcosta.marvelapp.wiki.presentation

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import com.github.felipehjcosta.marvelapp.wiki.datamodel.OthersCharactersDataModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class OthersCharactersViewModelTest {
    private val dataModel = mockk<OthersCharactersDataModel>()

    private val viewModel = OthersCharactersViewModel(dataModel)

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

        every { dataModel.getOthersCharacters() } returns Observable.just(listOf(character))

        val itemsObserver = TestObserver.create<List<CharacterItemViewModel>>()

        viewModel.items.subscribe(itemsObserver)

        val disposable = viewModel.loadItemsCommand.execute().subscribe()

        itemsObserver.assertValue { it[0].name == characterName }

        disposable.dispose()
    }

    @Test
    fun ensureShowLoadingEmitCorrectValuesWhenExecuteLoadItemsCommandCorrectly() {

        val characterName = "Wolverine"
        val character = Character().apply { name = characterName }

        every { dataModel.getOthersCharacters() } returns Observable.just(listOf(character))

        val itemsObserver = TestObserver.create<Boolean>()

        viewModel.showLoading.subscribe(itemsObserver)

        val disposable = viewModel.loadItemsCommand.execute().subscribe()

        itemsObserver.assertValues(true, false)

        disposable.dispose()
    }
}