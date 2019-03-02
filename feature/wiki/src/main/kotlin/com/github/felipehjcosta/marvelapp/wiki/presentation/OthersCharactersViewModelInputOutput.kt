package com.github.felipehjcosta.marvelapp.wiki.presentation

import com.felipecosta.rxaction.RxAction
import com.felipecosta.rxaction.RxCommand
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import com.github.felipehjcosta.marvelapp.wiki.datamodel.OthersCharactersDataModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OthersCharactersViewModelInputOutput @Inject constructor(
    private val dataModel: OthersCharactersDataModel
) : OthersCharactersViewModel(), OthersCharactersViewModelInput, OthersCharactersViewModelOutput {

    private val asyncLoadItemsCommand: RxAction<Any, List<Character>> = RxAction {
        dataModel.getOthersCharacters()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override val input: OthersCharactersViewModelInput = this
    override val output: OthersCharactersViewModelOutput = this

    override val loadItemsCommand: RxCommand<Any>
        get() = asyncLoadItemsCommand

    override val items: Observable<List<CharacterItemViewModel>>
        get() = asyncLoadItemsCommand.elements.map { list ->
            list.map { CharacterItemViewModel(it.id, it.name, it.thumbnail.url) }
        }
    override val showLoading: Observable<Boolean>
        get() = asyncLoadItemsCommand.executing
}
