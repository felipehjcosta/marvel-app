package com.github.felipehjcosta.marvelapp.detail.presentation

import com.felipecosta.rxaction.RxCommand
import io.reactivex.Observable

interface CharacterDetailViewModelInput {
    val characterCommand: RxCommand<Any>
}

interface CharacterDetailViewModelOutput {
    val name: Observable<String>
    val description: Observable<String>
    val thumbnailUrl: Observable<String>
    val comicsCount: Observable<Int>
    val eventsCount: Observable<Int>
    val seriesCount: Observable<Int>
    val storiesCount: Observable<Int>
}

abstract class CharacterDetailViewModel {
    abstract val input: CharacterDetailViewModelInput
    abstract val output: CharacterDetailViewModelOutput
}
