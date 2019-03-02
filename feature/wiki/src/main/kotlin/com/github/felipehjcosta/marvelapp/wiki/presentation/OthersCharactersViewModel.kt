package com.github.felipehjcosta.marvelapp.wiki.presentation

import com.felipecosta.rxaction.RxCommand
import io.reactivex.Observable

interface OthersCharactersViewModelInput {
    val loadItemsCommand: RxCommand<Any>
}

interface OthersCharactersViewModelOutput {
    val items: Observable<List<CharacterItemViewModel>>
    val showLoading: Observable<Boolean>
}

abstract class OthersCharactersViewModel {
    abstract val input: OthersCharactersViewModelInput
    abstract val output: OthersCharactersViewModelOutput
}
