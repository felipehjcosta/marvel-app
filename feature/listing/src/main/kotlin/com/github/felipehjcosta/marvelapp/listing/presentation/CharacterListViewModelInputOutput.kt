package com.github.felipehjcosta.marvelapp.listing.presentation

import com.felipecosta.rxaction.RxAction
import com.felipecosta.rxaction.RxCommand
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import com.github.felipehjcosta.marvelapp.listing.datamodel.ListingDataModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterListViewModelInputOutput @Inject constructor(
    private val dataModel: ListingDataModel
) : CharacterListViewModel(), CharacterListViewModelInput, CharacterListViewModelOutput {

    private val asyncLoadItemsCommand: RxAction<Any, List<Character>> = RxAction {
        dataModel.loadItems()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private val asyncLoadMoreCommand: RxAction<Any, List<Character>> = RxAction {
        currentItemsOffsetRelay
            .take(1)
            .flatMap { dataModel.loadItems(offset = it) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private val currentItemsOffsetRelay = BehaviorRelay.createDefault(0)
    
    override val input: CharacterListViewModelInput = this
    override val output: CharacterListViewModelOutput = this

    override val items: Observable<List<CharacterItemViewModel>>
        get() = asyncLoadItemsCommand.elements.map { it.size to it }
            .doOnNext { currentItemsOffsetRelay.accept(it.first) }
            .map { it.second }
            .map { it.map { CharacterItemViewModel(it.id, it.name, it.thumbnail.url) } }

    override val newItems: Observable<List<CharacterItemViewModel>>
        get() = asyncLoadMoreCommand.elements.map { currentItemsOffsetRelay.value!! + it.size to it }
            .doOnNext { currentItemsOffsetRelay.accept(it.first) }
            .map { it.second }
            .map { it.map { CharacterItemViewModel(it.id, it.name, it.thumbnail.url) } }

    override val showLoading: Observable<Boolean>
        get() = asyncLoadItemsCommand.executing

    override val showLoadItemsError: Observable<Boolean>
        get() = asyncLoadItemsCommand.errors.map { true }

    override val showLoadingMore: Observable<Boolean>
        get() = asyncLoadMoreCommand.executing

    override val loadItemsCommand: RxCommand<Any>
        get() = asyncLoadItemsCommand

    override val loadMoreItemsCommand: RxCommand<Any>
        get() = asyncLoadMoreCommand

}
