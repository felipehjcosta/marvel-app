package com.github.felipehjcosta.marvelapp.listing.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.base.navigator.AppNavigator
import com.github.felipehjcosta.marvelapp.base.rx.plusAssign
import com.github.felipehjcosta.marvelapp.listing.R
import com.github.felipehjcosta.marvelapp.listing.di.setupDependencyInjection
import com.github.felipehjcosta.marvelapp.listing.presentation.CharacterListViewModel
import com.github.felipehjcosta.recyclerviewdsl.onRecyclerView
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject
import com.github.felipehjcosta.marvelapp.listing.R.drawable.ic_arrow_back_white_24dp as navigationIconResId
import kotlinx.android.synthetic.main.listing_fragment.loading_view as loadingView
import kotlinx.android.synthetic.main.listing_fragment.recycler_view as recyclerView
import kotlinx.android.synthetic.main.listing_fragment.swipe_refresh_view as swipeRefreshView


class CharacterListingFragment : Fragment() {

    @Inject
    lateinit var viewModel: CharacterListViewModel

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var appNavigator: AppNavigator

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencyInjection()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.listing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManger = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManger

        bind(linearLayoutManger, loadingView, swipeRefreshView)
    }

    private fun bind(linearLayoutManger: LinearLayoutManager,
                     contentLoadingProgressBar: ContentLoadingProgressBar,
                     swipeRefresh: SwipeRefreshLayout) {
        compositeDisposable = CompositeDisposable()

        compositeDisposable += viewModel.items
                .subscribe {
                    onRecyclerView(recyclerView) {
                        bind(R.layout.listing_fragment_item) {
                            withItems(it) {

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

                                onClick { _, item ->
                                    activity?.let { appNavigator.showDetail(it, item?.id ?: 0) }
                                }
                            }
                        }
                    }
                }

        compositeDisposable += viewModel.showLoading
                .map { if (it) recyclerView to contentLoadingProgressBar else contentLoadingProgressBar to recyclerView }
                .subscribe { crossFade(it.first, it.second) }

        compositeDisposable += viewModel.showLoading.subscribe { swipeRefresh.isRefreshing = it }

        compositeDisposable += swipeRefresh.refreshes()
                .flatMapCompletable { viewModel.loadItemsCommand.execute() }
                .subscribe()

        compositeDisposable += viewModel.loadItemsCommand.execute().subscribe()

        compositeDisposable += viewModel.newItems
                .subscribe {
                    onRecyclerView(recyclerView) {
                        bind(R.layout.listing_fragment_item) {
                            addExtraItems(it)
                        }
                    }
                }

        val loadMoreCommand = viewModel.loadMoreItemsCommand

        compositeDisposable += Observable.combineLatest(recyclerView.scrollEvents(), Observable.just(linearLayoutManger),
                BiFunction { event: RecyclerViewScrollEvent, layoutManager: LinearLayoutManager ->
                    if (event.dy() > 0) {
                        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItem = layoutManager.findLastVisibleItemPosition()
                        val visibleThreshold = 5
                        (totalItemCount) < (firstVisibleItem + visibleThreshold)
                    } else {
                        false
                    }
                })
                .debounce(DEBOUNCE_SCROLL_TIMEOUT, MILLISECONDS, mainThread())
                .withLatestFrom(viewModel.showLoadingMore.startWith(false),
                        BiFunction { shouldLoadNewItems: Boolean, showLoadingMore: Boolean ->
                            if (showLoadingMore) false else shouldLoadNewItems
                        })
                .filter { it == true }
                .flatMapCompletable { loadMoreCommand.execute() }
                .subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbind()
    }

    private fun unbind() {
        compositeDisposable.dispose()
    }

    private fun crossFade(fromView: View, toView: View) {

        fromView.visibility = View.VISIBLE

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        toView.alpha = 0.0f
        toView.visibility = View.VISIBLE

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.

        val shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        toView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null)

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        fromView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        fromView.visibility = View.GONE
                    }
                })
    }

    companion object {

        private const val DEBOUNCE_SCROLL_TIMEOUT = 400L

        fun newInstance(): CharacterListingFragment {
            return CharacterListingFragment()
        }
    }
}
