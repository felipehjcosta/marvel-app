package com.github.felipehjcosta.marvelapp.wiki.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import com.github.felipehjcosta.marvelapp.wiki.R

class LoadingHighlightedCharacterItemRecyclerViewAdapter(
        size: Int
) : RecyclerView.Adapter<LoadingHighlightedCharacterItemRecyclerViewAdapter.ViewHolder>() {

    private val items = arrayOfNulls<Any?>(size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent.context)
            .inflate(R.layout.loading_highlighted_characters_item, parent, false).run {
                ViewHolder(this)
            }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
