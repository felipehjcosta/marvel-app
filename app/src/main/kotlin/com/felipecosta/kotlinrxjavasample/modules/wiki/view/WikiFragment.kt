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
import com.github.felipehjcosta.layoutmanager.GalleryLayoutManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class WikiFragment : Fragment() {

    @Inject
    lateinit var highlightedCharactersViewModel: HighlightedCharactersViewModel

    @Inject
    lateinit var othersCharactersViewModel: OthersCharactersViewModel

    lateinit var compositeDisposable: CompositeDisposable

    lateinit var othersAdapter: OthersCharacterItemRecyclerViewAdapter

    lateinit var highlightedAdapter: HighlightedCharacterItemRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_wiki, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.let {
            var recyclerView: RecyclerView = it.findBy(R.id.others_characters_recycler_view)

            recyclerView.layoutManager = GridLayoutManager(context, 3)
            othersAdapter = OthersCharacterItemRecyclerViewAdapter()
            recyclerView.adapter = othersAdapter


            recyclerView = it.findBy(R.id.highlighted_characters_recycler_view)

            highlightedAdapter = HighlightedCharacterItemRecyclerViewAdapter()

            val layoutManager = GalleryLayoutManager().apply {
                itemTransformer = WikiGalleryItemTransformer()
            }
            layoutManager.attach(recyclerView)

            recyclerView.adapter = highlightedAdapter
            bind()
        }
    }

    private fun bind() {
        compositeDisposable = CompositeDisposable()

        compositeDisposable += othersCharactersViewModel.items
                .subscribe { othersAdapter.replaceItems(it) }

        compositeDisposable += othersCharactersViewModel.loadItemsCommand.execute().subscribe()

        compositeDisposable += highlightedCharactersViewModel.items
                .subscribe { highlightedAdapter.replaceItems(it) }

        compositeDisposable += highlightedCharactersViewModel.loadItemsCommand.execute().subscribe()
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
