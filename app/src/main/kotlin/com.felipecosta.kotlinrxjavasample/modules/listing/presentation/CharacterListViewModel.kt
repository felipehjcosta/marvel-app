package com.felipecosta.kotlinrxjavasample.modules.listing.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import com.felipecosta.kotlinrxjavasample.rx.Command
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterListViewModel(private val dataModel: ListingDataModel) {

    private val asyncLoadItemsCommand: AsyncCommand<List<Character>>

    init {
        asyncLoadItemsCommand = AsyncCommand {
            dataModel.loadItems()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    val items: Observable<List<CharacterItemViewModel>>
        get() = asyncLoadItemsCommand.execution
                .map { it.map { CharacterItemViewModel(it.id ?: -1, it.name) } }

    val loadItemsCommand: Command
        get() = asyncLoadItemsCommand

    val showLoading: Observable<Boolean>
        get() = asyncLoadItemsCommand.executing

    val showLoadItemsError: Observable<Boolean>
        get() = asyncLoadItemsCommand.errors.map { true }

}