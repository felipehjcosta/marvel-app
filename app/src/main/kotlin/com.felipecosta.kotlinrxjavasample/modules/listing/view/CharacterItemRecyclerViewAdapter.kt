package com.felipecosta.kotlinrxjavasample.modules.listing.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

class CharacterItemRecyclerViewAdapter : RecyclerView.Adapter<CharacterItemRecyclerViewAdapter.ViewHolder>() {

    private val characterItemViewModels: MutableList<CharacterItemViewModel> = mutableListOf()

    private val onItemSelectedRelay = PublishRelay.create<Int>()

    val onItemSelected: Observable<Int>
        get() = onItemSelectedRelay

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listing_fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mContentView.text = characterItemViewModels[position].name

        holder.itemView.setOnClickListener {
            onItemSelectedRelay.accept(characterItemViewModels[holder.adapterPosition].id)
        }
    }

    override fun getItemCount(): Int {
        return characterItemViewModels.size
    }

    fun replaceItems(newItems: List<CharacterItemViewModel>) {
        characterItemViewModels.clear()
        characterItemViewModels.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val mContentView: TextView

        init {
            mContentView = view.findViewById(R.id.content) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
