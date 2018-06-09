package com.github.felipehjcosta.marvelapp.wiki.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.felipehjcosta.layoutmanager.GalleryLayoutManager
import com.github.felipehjcosta.marvelapp.base.rx.plusAssign
import com.github.felipehjcosta.marvelapp.base.util.findBy
import com.github.felipehjcosta.marvelapp.base.util.navigateToDetail
import com.github.felipehjcosta.marvelapp.base.util.navigateToListing
import com.github.felipehjcosta.marvelapp.wiki.R
import com.github.felipehjcosta.marvelapp.wiki.di.setupDependencyInjection
import com.github.felipehjcosta.marvelapp.wiki.presentation.HighlightedCharactersViewModel
import com.github.felipehjcosta.marvelapp.wiki.presentation.OthersCharactersViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_wiki.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_wiki.highlighted_characters_container as highlightedCharactersContainer
import kotlinx.android.synthetic.main.fragment_wiki.highlighted_characters_recycler_view as highlightedCharactersRecyclerView
import kotlinx.android.synthetic.main.fragment_wiki.others_characters_recycler_view as othersCharactersRecyclerView


class WikiFragment : Fragment() {

    @Inject
    lateinit var highlightedCharactersViewModel: HighlightedCharactersViewModel

    @Inject
    lateinit var othersCharactersViewModel: OthersCharactersViewModel

    private lateinit var compositeDisposable: CompositeDisposable

    private var othersAdapter = OthersCharacterItemRecyclerViewAdapter()

    private var highlightedAdapter = HighlightedCharacterItemRecyclerViewAdapter()

    private lateinit var highlightedCharactersLayoutManager: GalleryLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencyInjection()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wiki, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        othersCharactersRecyclerView.layoutManager = GridLayoutManager(context, 3)

        highlightedCharactersLayoutManager = GalleryLayoutManager().apply {
            attach(highlightedCharactersRecyclerView)
        }

        toolbar.apply {
            inflateMenu(R.menu.wiki_toolbar_menu)
            setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.wiki_menu_search) {
                    activity?.let { navigateToListing(it) }
                    true
                } else {
                    false
                }
            }
        }

        bind()
    }

    private fun bind() {
        compositeDisposable = CompositeDisposable()

        compositeDisposable += highlightedCharactersViewModel.items
                .doOnNext {
                    WikiGalleryCallbacksHandler(it, highlightedCharactersContainer).apply {
                        highlightedCharactersLayoutManager.itemTransformer = this
                        highlightedCharactersLayoutManager.onItemSelectedListener = this
                    }
                }
                .subscribe {
                    highlightedAdapter.replaceItems(it)
                    highlightedCharactersRecyclerView.adapter = highlightedAdapter
                }

        compositeDisposable += highlightedCharactersViewModel.showLoading.filter { it == true }.subscribe {
            LoadingWikiGalleryCallbacksHandler().apply {
                highlightedCharactersLayoutManager.itemTransformer = this
            }
            view.findBy<RecyclerView>(R.id.highlighted_characters_recycler_view).apply {
                adapter = LoadingHighlightedCharacterItemRecyclerViewAdapter(7)
            }
        }

        compositeDisposable += highlightedAdapter.onItemSelected
                .subscribe { itemSelectedId ->
                    activity?.let { navigateToDetail(it, itemSelectedId) }
                }

        compositeDisposable += highlightedCharactersViewModel.loadItemsCommand.execute().subscribe()

        compositeDisposable += othersCharactersViewModel.items
                .subscribe {
                    othersAdapter.replaceItems(it)
                    othersCharactersRecyclerView.adapter = othersAdapter
                }

        compositeDisposable += othersCharactersViewModel.showLoading
                .filter { it == true }
                .subscribe {
                    othersCharactersRecyclerView.adapter = LoadingOthersCharacterItemRecyclerViewAdapter(7)
                }

        compositeDisposable += othersAdapter.onItemSelected
                .subscribe { itemSelectedId ->
                    activity?.let { navigateToDetail(it, itemSelectedId) }
                }

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
