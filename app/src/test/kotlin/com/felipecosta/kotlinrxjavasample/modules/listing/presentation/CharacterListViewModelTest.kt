package com.felipecosta.kotlinrxjavasample.modules.listing.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.utils.mock
import com.felipecosta.kotlinrxjavasample.utils.whenever
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CharacterListViewModelTest {

    var dataModel: ListingDataModel = mock()

    lateinit var viewModel: CharacterListViewModel

    @Before
    fun setUp() {
        viewModel = CharacterListViewModel(dataModel)

        RxJavaPlugins.reset()
        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }

        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun whenSubscribedToItemsWhenExecuteLoadItemsCommandThenReturnItems() {

        val characterName = "Wolverine"
        val character = Character()
        character.name = characterName

        whenever(dataModel.loadItems()).thenReturn(Observable.just(listOf(character)))

        val itemsObserver = TestObserver.create<List<CharacterItemViewModel>>()

        viewModel.items.subscribe(itemsObserver)

        val disposable = viewModel.loadItemsCommand.execute().subscribe()

        itemsObserver.assertValue { it[0].name == characterName }

        disposable.dispose()
    }

    @Test
    fun whenSubscribedToShowLoadingWhenExecuteLoadItemsCommandThenReturnItems() {

        val characterName = "Wolverine"
        val character = Character()
        character.name = characterName

        whenever(dataModel.loadItems()).thenReturn(Observable.just(listOf(character)))

        val itemsObserver = TestObserver.create<Boolean>()

        viewModel.showLoading.subscribe(itemsObserver)

        val disposable = viewModel.loadItemsCommand.execute().subscribe()

        itemsObserver.assertValues(true, false)

        disposable.dispose()
    }

    @Test
    fun whenSubscribedToShowItemsLoadErrorWhenExecuteLoadItemsCommandThenReturnItems() {

        val loadItemsException = IOException()

        whenever(dataModel.loadItems()).thenReturn(Observable.error(loadItemsException))

        val itemsObserver = TestObserver.create<Boolean>()

        viewModel.showLoadItemsError.subscribe(itemsObserver)

        val disposable = viewModel.loadItemsCommand.execute().subscribe()

        itemsObserver.assertValues(true)

        disposable.dispose()
    }

}