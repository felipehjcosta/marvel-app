package com.felipecosta.kotlinrxjavasample.modules.listing.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.detail.view.DetailActivity
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterListViewModel
import com.felipecosta.kotlinrxjavasample.rx.findBy
import com.felipecosta.kotlinrxjavasample.rx.plusAssign
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ListingFragment : Fragment() {

    @Inject
    lateinit var viewModel: CharacterListViewModel

    lateinit var compositeDisposable: CompositeDisposable

    lateinit var adapter: CharacterItemRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.listing_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findBy(R.id.listing_recycler_view)
        val linearLayoutManger = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManger
        adapter = CharacterItemRecyclerViewAdapter()
        recyclerView.adapter = adapter

        val loading: ContentLoadingProgressBar = view.findBy(R.id.listing_loading)
        val swipeRefresh: SwipeRefreshLayout = view.findBy(R.id.listing_swipe_refresh)

        bind(recyclerView, linearLayoutManger, loading, swipeRefresh)
    }

    private fun bind(recyclerView: RecyclerView,
                     linearLayoutManger: LinearLayoutManager,
                     contentLoadingProgressBar: ContentLoadingProgressBar,
                     swipeRefresh: SwipeRefreshLayout) {
        compositeDisposable = CompositeDisposable()

        compositeDisposable += viewModel.items
                .doOnNext { adapter.replaceItems(it) }
                .subscribe()

        compositeDisposable += adapter.onItemSelected
                .subscribe {
                    DetailActivity.startDetail(activity, it)
                }

        compositeDisposable += viewModel.showLoading
                .map { if (it) recyclerView to contentLoadingProgressBar else contentLoadingProgressBar to recyclerView }
                .subscribe { crossfade(it.first, it.second) }

        compositeDisposable += viewModel.showLoading.subscribe { swipeRefresh.isRefreshing = it }

        compositeDisposable += swipeRefresh.refreshes()
                .flatMapCompletable { viewModel.loadItemsCommand.execute() }
                .subscribe()

        compositeDisposable += viewModel.loadItemsCommand.execute().subscribe()

        compositeDisposable += viewModel.newItems
                .doOnNext { adapter.addItems(it) }
                .subscribe()

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
                .debounce(400L, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .withLatestFrom(viewModel.showLoadingMore.startWith(false), BiFunction {
                    shouldLoadNewItems: Boolean, showLoadingMore: Boolean ->
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

    private fun crossfade(fromView: View, toView: View) {

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

        fun newInstance(): ListingFragment {
            return ListingFragment()
        }
    }
}
