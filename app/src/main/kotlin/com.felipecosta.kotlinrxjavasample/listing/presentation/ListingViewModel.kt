package com.felipecosta.kotlinrxjavasample.listing.presentation

import com.felipecosta.kotlinrxjavasample.listing.model.DummyContent
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import com.felipecosta.kotlinrxjavasample.rx.Command
import io.reactivex.Observable

class ListingViewModel(private val asyncListCommand: AsyncCommand<List<DummyContent.DummyItem>>) {

    val items: Observable<List<DummyContent.DummyItem>>
        get() = asyncListCommand.execution

    val listCommand: Command
        get() = asyncListCommand


}