package com.felipecosta.kotlinrxjavasample.modules.listing.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel

class CharacterItemRecyclerViewAdapter(private val characterItemViewModels: List<CharacterItemViewModel>) : RecyclerView.Adapter<CharacterItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listing_fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mContentView.text = characterItemViewModels[position].name
    }

    override fun getItemCount(): Int {
        return characterItemViewModels.size
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
