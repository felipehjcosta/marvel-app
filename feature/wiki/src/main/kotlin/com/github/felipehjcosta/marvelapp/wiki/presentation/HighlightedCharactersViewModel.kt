package com.github.felipehjcosta.marvelapp.wiki.presentation

import com.felipecosta.rxaction.RxCommand
import io.reactivex.Observable

interface HighlightedCharactersViewModelInput {
    val loadItemsCommand: RxCommand<Any>
}

interface HighlightedCharactersViewModelOutput {
    val items: Observable<List<CharacterItemViewModel>>
    val showLoading: Observable<Boolean>
}

abstract class HighlightedCharactersViewModel {
    abstract val input: HighlightedCharactersViewModelInput
    abstract val output: HighlightedCharactersViewModelOutput
}
