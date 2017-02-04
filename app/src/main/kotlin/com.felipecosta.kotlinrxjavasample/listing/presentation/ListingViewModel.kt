package com.felipecosta.kotlinrxjavasample.listing.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import com.felipecosta.kotlinrxjavasample.rx.Command
import io.reactivex.Observable

class ListingViewModel(private val asyncListCommand: AsyncCommand<List<Character>>) {

    val items: Observable<List<Character>>
        get() = asyncListCommand.execution

    val listCommand: Command
        get() = asyncListCommand


}