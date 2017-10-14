package com.felipecosta.kotlinrxjavasample.modules.detail.presentation

import com.felipecosta.kotlinrxjavasample.data.FavoriteRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.rxaction.RxAction
import com.felipecosta.rxaction.RxCommand
import io.reactivex.Observable

class CharacterDetailViewModel(private val asyncCharacterCommand: RxAction<Any, Character>, private val favoriteRepository: FavoriteRepository) {

    private val characterObservable: Observable<Character> = asyncCharacterCommand.elements.share()

    val name: Observable<String>
        get() = characterObservable.map { it.name }

    val description: Observable<String>
        get() = characterObservable.map { it.description }

    val thumbnailUrl: Observable<String>
        get() = characterObservable.map { it.thumbnail.url }

    val comicsCount: Observable<Int>
        get() = characterObservable.map { it.comics.items.count() }

    val eventsCount: Observable<Int>
        get() = characterObservable.map { it.events.items.count() }

    val seriesCount: Observable<Int>
        get() = characterObservable.map { it.series.items.count() }

    val storiesCount: Observable<Int>
        get() = characterObservable.map { it.stories.items.count() }

    val isFavorite: Observable<Boolean>
        get() = favoriteRepository.isFavorite()

    val characterCommand: RxCommand<Any>
        get() = asyncCharacterCommand

    fun removeFavorite() {
        favoriteRepository.removeFavorite()
    }

    fun saveFavorite() {
        favoriteRepository.saveFavorite()
    }

}