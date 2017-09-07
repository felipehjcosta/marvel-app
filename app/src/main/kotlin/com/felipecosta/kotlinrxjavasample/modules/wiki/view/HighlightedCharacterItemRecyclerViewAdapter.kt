package com.felipecosta.kotlinrxjavasample.modules.wiki.view

import android.animation.AnimatorInflater
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.felipecosta.kotlinrxjavasample.rx.findBy
import com.jakewharton.rxrelay2.PublishRelay
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import io.reactivex.Observable

class HighlightedCharacterItemRecyclerViewAdapter : RecyclerView.Adapter<HighlightedCharacterItemRecyclerViewAdapter.ViewHolder>() {

    private val characterItemViewModels: MutableList<CharacterItemViewModel> = mutableListOf()

    private val onItemSelectedRelay = PublishRelay.create<Int>()

    val onItemSelected: Observable<Int>
        get() = onItemSelectedRelay

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent.context)
            .inflate(R.layout.highlighted_characters_fragment_item, parent, false).run {
        ViewHolder(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            contentView.text = characterItemViewModels[position].name

            val imageOptions = DisplayImageOptions.Builder()
                    .showImageOnLoading(R.color.image_default_color)
                    .showImageForEmptyUri(R.color.image_default_color)
                    .showImageOnFail(R.color.image_default_color)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build()

            ImageLoader.getInstance().displayImage(characterItemViewModels[position].image, imageView, imageOptions)

            itemView.setOnClickListener {
                onItemSelectedRelay.accept(characterItemViewModels[adapterPosition].id)
            }
        }
    }

    override fun getItemCount(): Int = characterItemViewModels.size

    fun replaceItems(newItems: List<CharacterItemViewModel>) {
        characterItemViewModels.clear()
        characterItemViewModels.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<CharacterItemViewModel>) {
        val positionStart = characterItemViewModels.size
        characterItemViewModels.addAll(newItems)
        notifyItemRangeInserted(positionStart, characterItemViewModels.size)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findBy(R.id.image)
        val contentView: TextView = view.findBy(R.id.title)

        init {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.stateListAnimator = AnimatorInflater.loadStateListAnimator(view.context, R.anim.card_view_elevation)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
