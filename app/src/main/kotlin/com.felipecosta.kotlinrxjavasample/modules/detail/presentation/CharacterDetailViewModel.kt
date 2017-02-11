package com.felipecosta.kotlinrxjavasample.detail.presentation

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

    val characterCommand: Command
        get() = asyncCharacterCommand

}