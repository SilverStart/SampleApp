package com.silverstar.sampleapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.silverstar.sampleapp.R
import com.silverstar.sampleapp.data.entity.Item
import java.text.SimpleDateFormat
import java.util.*

class LikedItemAdapter(
    private val itemClickListener: (Item) -> Unit,
    private val likedClickListener: (Item) -> Unit
) : ItemAdapter(itemClickListener, likedClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return LikedItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item, parent, false),
            {
                itemClickListener(itemList[it])
            },
            {
                likedClickListener(itemList[it])
            }
        )
    }

    class LikedItemViewHolder(
        v: View,
        itemClickListener: (Int) -> Unit,
        likedClickListener: (Int) -> Unit
    ) : ItemViewHolder(v, itemClickListener, likedClickListener) {

        private val tvDateTime: TextView = v.findViewById(R.id.tv_date_time)

        override fun bind(item: Item) {
            super.bind(item)
            tvDateTime.text = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())
                .format(Date(item.dateTime))
        }
    }
}