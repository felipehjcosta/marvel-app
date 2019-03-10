package com.github.felipehjcosta.marvelapp.listing.presentation

import com.felipecosta.rxaction.RxCommand
import io.reactivex.Observable

interface CharacterListViewModelInput {
    val loadItemsCommand: RxCommand<Any>
    val loadMoreItemsCommand: RxCommand<Any>
}

interface CharacterListViewModelOutput {
    val items: Observable<List<CharacterItemViewModel>>
    val showLoading: Observable<Boolean>
    val showLoadItemsError: Observable<Boolean>
    val newItems: Observable<List<CharacterItemViewModel>>
    val showLoadingMore: Observable<Boolean>
}

abstract class CharacterListViewModel {
    abstract val input: CharacterListViewModelInput
    abstract val output: CharacterListViewModelOutput
}
