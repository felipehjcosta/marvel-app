package com.felipecosta.kotlinrxjavasample.modules.listing.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.di.HasSubcomponentBuilders
import com.felipecosta.kotlinrxjavasample.modules.detail.view.DetailActivity
import com.felipecosta.kotlinrxjavasample.modules.listing.di.ListingComponent
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterListViewModel
import com.felipecosta.kotlinrxjavasample.rx.findBy
import com.felipecosta.kotlinrxjavasample.rx.plusAssign
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ListingFragment : Fragment() {

    @Inject
    lateinit var viewModel: CharacterListViewModel

    lateinit var compositeDisposable: CompositeDisposable

    lateinit var adapter: CharacterItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity.application as HasSubcomponentBuilders).
                getSubcomponentBuilder(ListingComponent.Builder::class).
                build().
                inject(this)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.listing_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findBy(R.id.listing_recycler_view)
        val linearLayoutManger = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManger
        adapter = CharacterItemRecyclerViewAdapter()
        recyclerView.adapter = adapter

        bind(recyclerView, linearLayoutManger)
    }

    private fun bind(recyclerView: RecyclerView,
                     linearLayoutManger: LinearLayoutManager) {
        compositeDisposable = CompositeDisposable()

        compositeDisposable += viewModel.items
                .doOnNext { adapter.replaceItems(it) }
                .subscribe()

        compositeDisposable += adapter.onItemSelected
                .subscribe {
                    DetailActivity.startDetail(activity, it)
                }

        compositeDisposable += viewModel.loadItemsCommand.execute().subscribe()

        compositeDisposable += viewModel.newItems
                .doOnNext { adapter.addItems(it) }
                .subscribe()

        val loadMoreCommand = viewModel.loadMoreItemsCommand

        compositeDisposable += Observable.combineLatest(recyclerView.scrollEvents(), Observable.just(linearLayoutManger),
                BiFunction { recyclerViewScrollEvent: RecyclerViewScrollEvent, layoutManager: LinearLayoutManager -> layoutManager })
                .map {
                    val visibleItemCount = it.childCount
                    val totalItemCount = it.itemCount
                    val pastVisibleItems = it.findFirstVisibleItemPosition()
                    (visibleItemCount + pastVisibleItems) >= totalItemCount
                }
                .filter { it == true }
                .concatMap { loadMoreCommand.execute() }
                .subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbind()
    }

    private fun unbind() {
        compositeDisposable.dispose()
    }

    companion object {

        fun newInstance(): ListingFragment {
            return ListingFragment()
        }
    }
}
