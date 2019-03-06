package com.github.felipehjcosta.marvelapp.detail.presentation

import com.felipecosta.rxaction.RxAction
import com.felipecosta.rxaction.RxCommand
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import com.github.felipehjcosta.marvelapp.detail.datamodel.DetailDataModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterDetailViewModelInputOutput @Inject constructor(
    private val characterId: Int,
    private val dataModel: DetailDataModel
) : CharacterDetailViewModel(), CharacterDetailViewModelInput, CharacterDetailViewModelOutput {

    override val input: CharacterDetailViewModelInput = this
    override val output: CharacterDetailViewModelOutput = this

    private val asyncLoadCharacterAction: RxAction<Any, Character> = RxAction {
        dataModel.character(characterId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override val characterCommand: RxCommand<Any>
        get() = asyncLoadCharacterAction

    private val characterObservable: Observable<Character> =
        asyncLoadCharacterAction.elements.share()

    override val name: Observable<String>
        get() = characterObservable.map { it.name }

    override val description: Observable<String>
        get() = characterObservable.map { it.description }

    override val thumbnailUrl: Observable<String>
        get() = characterObservable.map { it.thumbnail.url }

    override val comicsCount: Observable<Int>
        get() = characterObservable.map { it.comics.items.count() }

    override val eventsCount: Observable<Int>
        get() = characterObservable.map { it.events.items.count() }

    override val seriesCount: Observable<Int>
        get() = characterObservable.map { it.series.items.count() }

    override val storiesCount: Observable<Int>
        get() = characterObservable.map { it.stories.items.count() }

}
