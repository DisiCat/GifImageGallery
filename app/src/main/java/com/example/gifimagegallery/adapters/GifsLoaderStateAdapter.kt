package com.example.gifimagegallery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gifimagegallery.R
import com.example.gifimagegallery.databinding.GifLoadStateFooterViewItemBinding
import com.example.gifimagegallery.databinding.ItemErrorBinding
import com.example.gifimagegallery.databinding.ItemProgressBinding

class GifsLoaderStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<GifsLoaderStateAdapter.GifsLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: GifsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): GifsLoadStateViewHolder {
        return GifsLoadStateViewHolder.create(parent, retry)
    }

    class GifsLoadStateViewHolder(
        private val binding: GifLoadStateFooterViewItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsgTextView.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsgTextView.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): GifsLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.gif_load_state_footer_view_item, parent, false)
                val binding = GifLoadStateFooterViewItemBinding.bind(view)
                return GifsLoadStateViewHolder(binding, retry)
            }
        }
    }
}