package com.felipecosta.kotlinrxjavasample.modules.detail.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import com.felipecosta.kotlinrxjavasample.rx.Command
import io.reactivex.Observable

class CharacterDetailViewModel(private val asyncCharacterCommand: AsyncCommand<Character>) {

    private val characterObservable: Observable<Character> = asyncCharacterCommand.execution.share()

    val name: Observable<String>
        get() = characterObservable.map { it.name }

    val description: Observable<String>
        get() = characterObservable.map { it.description }

    val thumbnailUrl: Observable<String>
        get() = characterObservable.map { it.thumbnail?.path + "." + it.thumbnail?.extension }

    val comicsCount: Observable<Int>
        get() = characterObservable.map { it.comics?.items?.count() }

    val eventsCount: Observable<Int>
        get() = characterObservable.map { it.events?.items?.count() }

    val seriesCount: Observable<Int>
        get() = characterObservable.map { it.series?.items?.count() }

    val storiesCount: Observable<Int>
        get() = characterObservable.map { it.stories?.items?.count() }

    val characterCommand: Command
        get() = asyncCharacterCommand

}