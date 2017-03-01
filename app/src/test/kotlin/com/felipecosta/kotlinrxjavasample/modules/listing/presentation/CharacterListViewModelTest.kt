package com.felipecosta.kotlinrxjavasample.modules.listing.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.rx.plusAssign
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
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
    fun subscribedToItemsWhenExecuteLoadItemsCommandThenReturnItems() {

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
    fun subscribedToShowLoadingWhenExecuteLoadItemsCommandThenReturnItems() {

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
    fun subscribedToShowItemsLoadErrorWhenExecuteLoadItemsCommandThenReturnItems() {

        val loadItemsException = IOException()

        whenever(dataModel.loadItems()).thenReturn(Observable.error(loadItemsException))

        val itemsObserver = TestObserver.create<Boolean>()

        viewModel.showLoadItemsError.subscribe(itemsObserver)

        val disposable = viewModel.loadItemsCommand.execute().subscribe()

        itemsObserver.assertValues(true)

        disposable.dispose()
    }

    @Test
    fun subscribedToNewItemsWhenExecuteLoadMoreItemsCommandThenReturnItems() {

        val characterName = "Wolverine"
        val character = Character()
        character.name = characterName

        whenever(dataModel.loadItems()).thenReturn(Observable.just(listOf(character)))

        val itemsObserver = TestObserver.create<List<CharacterItemViewModel>>()

        viewModel.newItems.subscribe(itemsObserver)

        val disposable = viewModel.loadMoreItemsCommand.execute().subscribe()

        itemsObserver.assertValue { it[0].name == characterName }

        disposable.dispose()
    }

    @Test
    fun subscribedToShowLoadingMoreWhenExecuteLoadMoreItemsCommandThenAssertNormalLoadingFlow() {
        val characterName = "Wolverine"
        val character = Character()
        character.name = characterName

        whenever(dataModel.loadItems()).thenReturn(Observable.just(listOf(character)))

        val showLoadMoreObserver = TestObserver.create<Boolean>()

        viewModel.showLoadingMore.subscribe(showLoadMoreObserver)

        val disposable = viewModel.loadMoreItemsCommand.execute().subscribe()

        disposable.dispose()

        showLoadMoreObserver.assertValues(true, false)
    }

    @Test
    fun subscribedToItemsAndNewItemsWhenExecuteLoadMoreAfterLoadCommandThenReturnItemsAndNewItems() {

        val characterName1 = "Wolverine"
        val character = Character()
        character.name = characterName1

        whenever(dataModel.loadItems()).thenReturn(Observable.just(listOf(character)))
        val characterName2 = "Spiner-Man"
        val character2 = Character()
        character2.name = characterName2

        whenever(dataModel.loadItems(offset = 1)).thenReturn(Observable.just(listOf(character2)))

        val itemsObserver = TestObserver.create<List<CharacterItemViewModel>>()

        viewModel.items.subscribe(itemsObserver)

        val newItemsObserver = TestObserver.create<List<CharacterItemViewModel>>()
        viewModel.newItems.subscribe(newItemsObserver)

        val disposables = CompositeDisposable()

        disposables += viewModel.loadItemsCommand.execute().subscribe()
        disposables += viewModel.loadMoreItemsCommand.execute().subscribe()

        itemsObserver.assertValueAt(0) { it[0].name == characterName1 }
        newItemsObserver.assertValueAt(0) { it[0].name == characterName2 }

        disposables.dispose()
    }

    @Test
    fun subscribedToItemsAndNewItemsWhenExecuteLoadMoreAfterLoadThenVerifyCorrectlyInteractionOnMock() {

        val characterName1 = "Wolverine"
        val character = Character()
        character.name = characterName1

        whenever(dataModel.loadItems()).thenReturn(Observable.just(listOf(character)))
        val characterName2 = "Spiner-Man"
        val character2 = Character()
        character2.name = characterName2

        whenever(dataModel.loadItems(offset = 1)).thenReturn(Observable.just(listOf(character2)))

        val itemsObserver = TestObserver.create<List<CharacterItemViewModel>>()

        viewModel.items.subscribe(itemsObserver)

        val newItemsObserver = TestObserver.create<List<CharacterItemViewModel>>()
        viewModel.newItems.subscribe(newItemsObserver)

        val disposables = CompositeDisposable()

        disposables += viewModel.loadItemsCommand.execute().subscribe()
        disposables += viewModel.loadMoreItemsCommand.execute().subscribe()

        verify(dataModel, times(2)).loadItems(any(), any())

        disposables.dispose()
    }

}