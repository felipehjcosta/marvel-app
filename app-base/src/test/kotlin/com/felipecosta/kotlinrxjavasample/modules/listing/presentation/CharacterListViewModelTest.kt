package com.felipecosta.kotlinrxjavasample.modules.listing.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.rx.plusAssign
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable.error
import io.reactivex.Observable.just
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CharacterListViewModelTest {

    private var dataModel = mockk<ListingDataModel>()

    private val viewModel = CharacterListViewModel(dataModel)

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
        val character = Character()
        character.name = characterName

        every { dataModel.loadItems() } returns just(listOf(character))

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

        every { dataModel.loadItems() } returns just(listOf(character))

        val itemsObserver = TestObserver.create<Boolean>()

        viewModel.showLoading.subscribe(itemsObserver)

        val disposable = viewModel.loadItemsCommand.execute().subscribe()

        itemsObserver.assertValues(true, false)

        disposable.dispose()
    }

    @Test
    fun subscribedToShowItemsLoadErrorWhenExecuteLoadItemsCommandThenReturnItems() {

        val loadItemsException = IOException()

        every { dataModel.loadItems() } returns error(loadItemsException)

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

        every { dataModel.loadItems() } returns just(listOf(character))

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

        every { dataModel.loadItems() } returns just(listOf(character))

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

        every { dataModel.loadItems() } returns just(listOf(character))
        val characterName2 = "Spiner-Man"
        val character2 = Character()
        character2.name = characterName2

        every { dataModel.loadItems(offset = 1) } returns just(listOf(character2))

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

        every { dataModel.loadItems() } returns just(listOf(character))
        val characterName2 = "Spiner-Man"
        val character2 = Character()
        character2.name = characterName2

        every { dataModel.loadItems(offset = 1) } returns just(listOf(character2))

        val itemsObserver = TestObserver.create<List<CharacterItemViewModel>>()

        viewModel.items.subscribe(itemsObserver)

        val newItemsObserver = TestObserver.create<List<CharacterItemViewModel>>()
        viewModel.newItems.subscribe(newItemsObserver)

        val disposables = CompositeDisposable()

        disposables += viewModel.loadItemsCommand.execute().subscribe()
        disposables += viewModel.loadMoreItemsCommand.execute().subscribe()

        verify(exactly = 2) { dataModel.loadItems(any(), any()) }

        disposables.dispose()
    }

}