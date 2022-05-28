package com.example.gifimagegallery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gifimagegallery.R
import com.example.gifimagegallery.databinding.GifItemLayoutBinding
import com.example.gifimagegallery.network.parseModels.Data
import com.example.gifimagegallery.ui.main.viewModels.UiModel

class GifsAdapter() : PagingDataAdapter<UiModel, GifsAdapter.GifsViewHolder>(UIMODEL_COMPARATOR) {


    class GifsViewHolder(private val itemGifBinding: GifItemLayoutBinding) :
        RecyclerView.ViewHolder(itemGifBinding.root) {
        fun bind(item: Data) {
            Glide.with(itemGifBinding.root).load(item.images?.original?.url).into(itemGifBinding.gifImageView)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GifsViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.gif_item_layout,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val uiModel = getItem(position)
        if (uiModel is UiModel.GIFItem)
            holder.bind(uiModel.gif)

    }


    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.GIFItem && newItem is UiModel.GIFItem &&
                        oldItem.gif.id == newItem.gif.id)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean =
                oldItem == newItem
        }
    }

}