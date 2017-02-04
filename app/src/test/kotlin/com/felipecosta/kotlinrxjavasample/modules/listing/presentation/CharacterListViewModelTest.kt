package com.felipecosta.kotlinrxjavasample.modules.listing.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class CharacterListViewModelTest {

    lateinit var commandActionSubject: PublishSubject<List<Character>>

    lateinit var viewModel: CharacterListViewModel

    @Before
    fun setUp() {

        commandActionSubject = PublishSubject.create()

        val asyncCommand: AsyncCommand<List<Character>> = AsyncCommand({ commandActionSubject })

        viewModel = CharacterListViewModel(asyncCommand)
    }

    @Test
    fun whenCallItemsThenReturnItems() {

        val characterName = "Wolverine"
        val character = Character()
        character.name = characterName

        val itemsObserver = TestObserver.create<List<CharacterItemViewModel>>()

        viewModel.items.subscribe(itemsObserver)

        val disposable = viewModel.listCommand.execute().subscribe()

        commandActionSubject.onNext(listOf(character))

        itemsObserver.assertValue { it[0].name == characterName }

        disposable.dispose()
    }
}