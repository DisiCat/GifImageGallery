package com.example.gifimagegallery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gifimagegallery.databinding.GifItemLayoutBinding
import com.example.gifimagegallery.entity.GifItemView
import com.example.gifimagegallery.ui.main.viewModels.UiModel

class GifsAdapter(
   private val removeItem : (String?) -> Unit
) : PagingDataAdapter<UiModel, GifsAdapter.GifsViewHolder>(UIMODEL_COMPARATOR) {


    class GifsViewHolder(private val itemGifBinding: GifItemLayoutBinding) :
        RecyclerView.ViewHolder(itemGifBinding.root) {
        fun bind(item: GifItemView, removeItem: (String?) -> Unit) {
            Glide.with(itemGifBinding.root).load(item.url).into(itemGifBinding.gifImageView)
            itemGifBinding.removeItemImageVIew.setOnClickListener {
                removeItem(item.id)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GifsViewHolder(
        GifItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val uiModel = getItem(position)
        if (uiModel is UiModel.GIFItem)
            holder.bind(uiModel.gif,removeItem)

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