package com.felipecosta.kotlinrxjavasample.modules.listing.view

import android.animation.AnimatorInflater
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.felipecosta.kotlinrxjavasample.util.bindView
import com.jakewharton.rxrelay2.PublishRelay
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import io.reactivex.Observable

class CharacterItemRecyclerViewAdapter : RecyclerView.Adapter<CharacterItemRecyclerViewAdapter.ViewHolder>() {

    private val characterItemViewModels: MutableList<CharacterItemViewModel> = mutableListOf()

    private val onItemSelectedRelay = PublishRelay.create<Int>()

    val onItemSelected: Observable<Int>
        get() = onItemSelectedRelay

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = from(parent.context)
            .inflate(R.layout.listing_fragment_item, parent, false).run {
        ViewHolder(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            contentView.text = characterItemViewModels[position].name

            val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.image_default_color_radius)

            val imageOptions = DisplayImageOptions.Builder()
                    .displayer(RoundedBitmapDisplayer(cornerRadius))
                    .showImageOnLoading(R.drawable.ic_rounded_image_default)
                    .showImageForEmptyUri(R.drawable.ic_rounded_image_default)
                    .showImageOnFail(R.drawable.ic_rounded_image_default)
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
        val imageView: ImageView by bindView(R.id.image)
        val contentView: TextView by bindView(R.id.title)

        init {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                view.stateListAnimator = AnimatorInflater.loadStateListAnimator(view.context, R.anim.card_view_elevation)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}
