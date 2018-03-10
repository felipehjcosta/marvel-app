package com.felipecosta.kotlinrxjavasample.modules.wiki.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.felipecosta.kotlinrxjavasample.modules.wiki.datamodel.OthersCharactersDataModel
import com.felipecosta.rxaction.RxAction
import com.felipecosta.rxaction.RxCommand
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OthersCharactersViewModel @Inject constructor(
        private val dataModel: OthersCharactersDataModel
) {

    private val asyncLoadItemsCommand: RxAction<Any, List<Character>> = RxAction {
        dataModel.getOthersCharacters()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    val loadItemsCommand: RxCommand<Any>
        get() = asyncLoadItemsCommand

    val items: Observable<List<CharacterItemViewModel>>
        get() = asyncLoadItemsCommand.elements.map {
            it.map {
                CharacterItemViewModel(it.id, it.name, it.thumbnail.url)
            }
        }
}