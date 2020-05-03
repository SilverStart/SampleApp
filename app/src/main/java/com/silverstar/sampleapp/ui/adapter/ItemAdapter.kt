package com.silverstar.sampleapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.silverstar.sampleapp.R
import com.silverstar.sampleapp.data.entity.Item

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var itemList: List<Item> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item, parent, false)
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun setItems(items: List<Item>) {
        val callback = ItemDiffCallback(itemList, items)
        val result = DiffUtil.calculateDiff(callback)

        itemList = items
        result.dispatchUpdatesTo(this)
    }

    class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val tvName: TextView = v.findViewById(R.id.tv_name)
        private val tvRate: TextView = v.findViewById(R.id.tv_rate)
        private val cbLiked: CheckBox = v.findViewById(R.id.cb_liked)
        private val ivThumbnail: ImageView = v.findViewById(R.id.iv_thumbnail)

        fun bind(item: Item) {
            tvName.text = item.name
            tvRate.text = tvRate.context.getString(R.string.rate_fmt, item.rate)
            cbLiked.isChecked = item.liked
            Glide.with(ivThumbnail).load(item.thumbnailUrl).into(ivThumbnail)
        }
    }
}