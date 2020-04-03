package com.onoffrice.dogsbreed.ui.dogsFeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.data.local.FeedItem
import com.onoffrice.dogsbreed.utils.extensions.loadImage
import kotlinx.android.synthetic.main.feed_item.view.*
import java.util.*


class FeedAdapter (private val listener: ItemClickListener): RecyclerView.Adapter<FeedAdapter.DogFeedViewHolderItem>() {

    interface ItemClickListener {
        fun onClickCharacter(position: FeedItem)
        fun removeImage()
    }

    var list: List<FeedItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): DogFeedViewHolderItem {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)

        return DogFeedViewHolderItem(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DogFeedViewHolderItem, position: Int) {
        //holder.itemView.fadeUpItemListAnimation(position,200)
        var wasLong = false;
        val timer = Timer()
        val feedItem = list[position]

        feedItem.let {
            holder.poster.loadImage(it.imageUrl)
            if (it.isItemSelected) {
                holder.itemView.animation =
                    AnimationUtils.loadAnimation(holder.poster.context, R.anim.zoom_in)
            }

            // Feed item click listener
            holder.itemView.setOnTouchListener { _, event ->

            }
        }
    }

    class DogFeedViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster= itemView.poster
    }
}
