package com.felipecosta.kotlinrxjavasample.modules.wiki.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.wiki.presentation.HighlightedCharactersViewModel
import com.felipecosta.kotlinrxjavasample.modules.wiki.presentation.OthersCharactersViewModel
import com.felipecosta.kotlinrxjavasample.rx.findBy
import com.felipecosta.kotlinrxjavasample.rx.plusAssign
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class WikiFragment : Fragment() {

    @Inject
    lateinit var highlightedCharactersViewModel: HighlightedCharactersViewModel

    @Inject
    lateinit var othersCharactersViewModel: OthersCharactersViewModel

    lateinit var compositeDisposable: CompositeDisposable

    lateinit var adapter: OthersCharacterItemRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_wiki, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.let {
            val recyclerView: RecyclerView = it.findBy(R.id.others_characters_recycler_view)

            recyclerView.layoutManager = GridLayoutManager(context, 3)
            adapter = OthersCharacterItemRecyclerViewAdapter()
            recyclerView.adapter = adapter

            bind()
        }
    }

    private fun bind() {
        compositeDisposable = CompositeDisposable()

        compositeDisposable += othersCharactersViewModel.items
                .doOnNext { adapter.replaceItems(it) }
                .subscribe()

        compositeDisposable += othersCharactersViewModel.loadItemsCommand.execute().subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbind()
    }

    private fun unbind() {
        compositeDisposable.dispose()
    }

    companion object {
        fun newInstance() = WikiFragment()
    }

}
