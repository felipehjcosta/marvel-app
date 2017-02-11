package com.felipecosta.kotlinrxjavasample.modules.listing.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import com.felipecosta.kotlinrxjavasample.rx.Command
import io.reactivex.Observable

class CharacterListViewModel(private val asyncListCommand: AsyncCommand<List<Character>>) {

    val items: Observable<List<CharacterItemViewModel>>
        get() = asyncListCommand.execution.map { it.map { CharacterItemViewModel(it.id ?: -1, it.name) } }

    val listCommand: Command
        get() = asyncListCommand

}