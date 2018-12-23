package com.github.felipehjcosta.marvelapp.wiki.presentation

import com.felipecosta.rxaction.RxAction
import com.felipecosta.rxaction.RxCommand
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import com.github.felipehjcosta.marvelapp.wiki.datamodel.HighlightedCharactersDataModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HighlightedCharactersViewModel @Inject constructor(
    dataModel: HighlightedCharactersDataModel
) {

    private val asyncLoadItemsCommand: RxAction<Any, List<Character>> = RxAction {
        dataModel.getHighlightedCharacters()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    val loadItemsCommand: RxCommand<Any>
        get() = asyncLoadItemsCommand

    val items: Observable<List<CharacterItemViewModel>>
        get() = asyncLoadItemsCommand.elements.map { list ->
            list.map {
                CharacterItemViewModel(it.id, it.name, it.thumbnail.url)
            }
        }

    val showLoading: Observable<Boolean>
        get() = asyncLoadItemsCommand.executing
}
