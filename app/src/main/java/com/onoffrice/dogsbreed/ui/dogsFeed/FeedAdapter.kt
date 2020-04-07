package com.onoffrice.dogsbreed.ui.dogsFeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.data.local.FeedItem
import com.onoffrice.dogsbreed.utils.extensions.loadImage
import kotlinx.android.synthetic.main.feed_item.view.*


class FeedAdapter (private val listener: ItemClickListener): RecyclerView.Adapter<FeedAdapter.DogFeedViewHolderItem>() {

    interface ItemClickListener {
        fun onClickCharacter(item: FeedItem)
    }

    var list: MutableList<FeedItem> = mutableListOf()
        set(value) {
            field.clear()
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): DogFeedViewHolderItem {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.feed_item, parent, false)

        return DogFeedViewHolderItem(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DogFeedViewHolderItem, position: Int) {
        val feedItem = list[position]

        feedItem.let {
            holder.poster.loadImage(it.imageUrl)
            holder.itemView.setOnClickListener { listener.onClickCharacter(feedItem) }
        }
    }

    class DogFeedViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster= itemView.poster
    }
}
