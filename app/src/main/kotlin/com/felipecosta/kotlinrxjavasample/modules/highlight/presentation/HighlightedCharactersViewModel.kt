package com.felipecosta.kotlinrxjavasample.modules.highlight.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.highlight.datamodel.HighlightedCharactersDataModel
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import com.felipecosta.kotlinrxjavasample.rx.Command
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HighlightedCharactersViewModel(dataModel: HighlightedCharactersDataModel) {

    private val asyncLoadItemsCommand: AsyncCommand<List<Character>> = AsyncCommand {
        dataModel.getHighlightedCharacters()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    val loadItemsCommand: Command
        get() = asyncLoadItemsCommand

    val items: Observable<List<CharacterItemViewModel>>
        get() = asyncLoadItemsCommand.execution.map {
            it.map {
                CharacterItemViewModel(it.id, it.name, it.thumbnail.url)
            }
        }
}