package com.onoffrice.dogsbreed.ui.dogsFeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onoffrice.dogsbreed.R
import com.onoffrice.dogsbreed.utils.extensions.loadImage
import kotlinx.android.synthetic.main.feed_item.view.*

class FeedAdapter (private val listener: ItemClickListener): RecyclerView.Adapter<FeedAdapter.DogFeedViewHolderItem>() {

    interface ItemClickListener {
        fun onClickCharacter(feedView: View)
    }

    var list: List<String> = mutableListOf()
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

        val feedItem = list[position]

        feedItem.let { feedUrl ->
            holder.poster.loadImage(feedUrl)

            // Feed item click listener
            holder.itemView.setOnClickListener {
                listener.onClickCharacter(holder.itemView)
            }
        }
    }

    class DogFeedViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster= itemView.poster
    }
}
