package com.github.felipehjcosta.marvelapp.wiki.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.github.felipehjcosta.layoutmanager.GalleryLayoutManager
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.base.navigator.AppNavigator
import com.github.felipehjcosta.marvelapp.base.rx.plusAssign
import com.github.felipehjcosta.marvelapp.wiki.R
import com.github.felipehjcosta.marvelapp.wiki.di.setupDependencyInjection
import com.github.felipehjcosta.marvelapp.wiki.presentation.CharacterItemViewModel
import com.github.felipehjcosta.marvelapp.wiki.presentation.HighlightedCharactersViewModel
import com.github.felipehjcosta.marvelapp.wiki.presentation.OthersCharactersViewModel
import com.github.felipehjcosta.recyclerviewdsl.onRecyclerView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_wiki.highlighted_characters_container as highlightedCharactersContainer
import kotlinx.android.synthetic.main.fragment_wiki.highlighted_characters_recycler_view as highlightedCharactersRecyclerView
import kotlinx.android.synthetic.main.fragment_wiki.others_characters_recycler_view as othersCharactersRecyclerView


class WikiFragment : Fragment() {

    @Inject
    lateinit var highlightedCharactersViewModel: HighlightedCharactersViewModel

    @Inject
    lateinit var othersCharactersViewModel: OthersCharactersViewModel

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var appNavigator: AppNavigator

    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var highlightedCharactersLayoutManager: GalleryLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencyInjection()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wiki, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        highlightedCharactersLayoutManager = GalleryLayoutManager().apply {
            attach(highlightedCharactersRecyclerView)
        }

        bind()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.wiki_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.wiki_menu_search -> {
            activity?.let { appNavigator.showList(it) }
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun bind() {
        compositeDisposable = CompositeDisposable()
        bindHighlightedCharactersSection()
        bindOthersCharactersSection()
    }

    private fun bindHighlightedCharactersSection() {
        compositeDisposable += highlightedCharactersViewModel.items
                .doOnNext {
                    WikiGalleryCallbacksHandler(imageLoader, it, highlightedCharactersContainer).apply {
                        highlightedCharactersLayoutManager.itemTransformer = this
                        highlightedCharactersLayoutManager.onItemSelectedListener = this
                    }
                }
                .subscribe { displayHighlightedCharacters(it) }

        compositeDisposable += highlightedCharactersViewModel.showLoading
                .filter { it == true }
                .doOnNext {
                    LoadingWikiGalleryCallbacksHandler().apply {
                        highlightedCharactersLayoutManager.itemTransformer = this
                    }
                }
                .subscribe { displayLoadingHighlightedCharacters() }

    }

    private fun displayLoadingHighlightedCharacters() {
        onRecyclerView(highlightedCharactersRecyclerView) {
            bind(R.layout.loading_highlighted_characters_item) {
                withItems(arrayOfNulls<Any?>(7).toList()) {
                }
            }
        }
    }

    private fun displayHighlightedCharacters(list: List<CharacterItemViewModel>) {
        onRecyclerView(highlightedCharactersRecyclerView) {
            bind(R.layout.highlighted_characters_fragment_item) {
                withItems(list) {
                    on<TextView>(R.id.title) {
                        it.view?.text = it.item?.name
                    }

                    on<ImageView>(R.id.image) {
                        val imageUrl = it.item?.image
                        val imageView = it.view
                        if (imageUrl != null && imageView != null) {
                            imageLoader.loadImage(imageUrl, imageView)
                        }
                    }

                    onClick { _, characterItemViewModel ->
                        activity?.let {
                            appNavigator.showDetail(it, characterItemViewModel?.id ?: 0L)
                        }
                    }
                }
            }
        }
    }

    private fun bindOthersCharactersSection() {
        compositeDisposable += highlightedCharactersViewModel.loadItemsCommand.execute().subscribe()

        compositeDisposable += othersCharactersViewModel.items
                .subscribe { displayOthersCharacters(it) }

        compositeDisposable += othersCharactersViewModel.showLoading
                .filter { it == true }
                .subscribe { displayLoadingOthersCharacters() }

        compositeDisposable += othersCharactersViewModel.loadItemsCommand.execute().subscribe()
    }

    private fun displayLoadingOthersCharacters() {
        onRecyclerView(othersCharactersRecyclerView) {
            withGridLayout { spanCount = 3 }

            bind(R.layout.loading_others_characters_item) {
                withItems(arrayOfNulls<Any?>(7).toList()) {
                }
            }
        }
    }

    private fun displayOthersCharacters(list: List<CharacterItemViewModel>) {
        onRecyclerView(othersCharactersRecyclerView) {
            withGridLayout { spanCount = 3 }

            bind(R.layout.others_characters_fragment_item) {
                withItems(list) {
                    on<TextView>(R.id.title) {
                        it.view?.text = it.item?.name
                    }

                    on<ImageView>(R.id.image) {
                        val imageUrl = it.item?.image
                        val imageView = it.view
                        if (imageUrl != null && imageView != null) {
                            val cornerRadius = imageView.resources
                                    .getDimensionPixelSize(com.github.felipehjcosta.marvelapp.base.R.dimen.image_default_color_radius)
                            imageLoader.loadRoundedImage(imageUrl, imageView, cornerRadius)
                        }
                    }

                    onClick { _, characterItemViewModel ->
                        activity?.let {
                            appNavigator.showDetail(it, characterItemViewModel?.id ?: 0)
                        }
                    }
                }
            }
        }
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
