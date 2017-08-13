package com.felipecosta.kotlinrxjavasample.modules.listing.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import com.felipecosta.kotlinrxjavasample.rx.Command
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharacterListViewModel(private val dataModel: ListingDataModel) {

    private val asyncLoadItemsCommand: AsyncCommand<List<Character>>

    private val asyncLoadMoreCommand: AsyncCommand<List<Character>>

    private val currentItemsOffsetRelay = BehaviorRelay.createDefault(0)

    init {
        asyncLoadItemsCommand = AsyncCommand {
            dataModel.loadItems()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        asyncLoadMoreCommand = AsyncCommand {
            currentItemsOffsetRelay
                    .take(1)
                    .flatMap { dataModel.loadItems(offset = it) }
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    val items: Observable<List<CharacterItemViewModel>>
        get() = asyncLoadItemsCommand.execution.map { it.size to it }
                .doOnNext { currentItemsOffsetRelay.accept(it.first) }
                .map { it.second }
                .map { it.map { CharacterItemViewModel(it.id, it.name, it.thumbnail.url) } }

    val newItems: Observable<List<CharacterItemViewModel>>
        get() = asyncLoadMoreCommand.execution.map { currentItemsOffsetRelay.value + it.size to it }
                .doOnNext { currentItemsOffsetRelay.accept(it.first) }
                .map { it.second }
                .map { it.map { CharacterItemViewModel(it.id, it.name, it.thumbnail.url) } }

    val loadItemsCommand: Command
        get() = asyncLoadItemsCommand

    val loadMoreItemsCommand: Command
        get() = asyncLoadMoreCommand

    val showLoading: Observable<Boolean>
        get() = asyncLoadItemsCommand.executing

    val showLoadItemsError: Observable<Boolean>
        get() = asyncLoadItemsCommand.errors.map { true }

    val showLoadingMore: Observable<Boolean>
        get() = asyncLoadMoreCommand.executing

}