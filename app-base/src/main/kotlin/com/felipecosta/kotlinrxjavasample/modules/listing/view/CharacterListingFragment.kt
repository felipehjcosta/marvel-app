package com.felipecosta.kotlinrxjavasample.modules.listing.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.CATEGORY_BROWSABLE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat.getDrawable
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.net.toUri
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterListViewModel
import com.felipecosta.kotlinrxjavasample.rx.plusAssign
import com.felipecosta.kotlinrxjavasample.util.bindView
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject


class CharacterListingFragment : Fragment() {

    @Inject
    lateinit var viewModel: CharacterListViewModel

    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var adapter: CharacterItemRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.listing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView by bindView(R.id.listing_recycler_view)
        val linearLayoutManger = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManger
        adapter = CharacterItemRecyclerViewAdapter()
        recyclerView.adapter = adapter

        val loading: ContentLoadingProgressBar by bindView(R.id.listing_loading)
        val swipeRefresh: SwipeRefreshLayout by bindView(R.id.listing_swipe_refresh)

        val toolbar: Toolbar by bindView(R.id.toolbar)
        toolbar.navigationIcon = getDrawable(resources, android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material, null)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

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
                .subscribe { itemSelectedId ->
                    activity?.let {
                        val uri = "https://marvelapp.instantappsample.com/detail/${itemSelectedId}".toUri()
                        Intent(ACTION_VIEW, uri).apply {
                            addCategory(CATEGORY_BROWSABLE)
                            `package` = it.packageName
                            startActivity(this)
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
