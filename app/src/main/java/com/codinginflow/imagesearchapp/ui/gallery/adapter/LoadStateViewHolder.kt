package com.codinginflow.imagesearchapp.ui.gallery.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.imagesearchapp.databinding.UnsplashPhotoLoadStateFooterBinding
import com.codinginflow.imagesearchapp.ui.gallery.UnsplashPhotoLoadStateAdapter

class LoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    constructor(binding: UnsplashPhotoLoadStateFooterBinding, retry: () -> Unit) : this(binding)

    val unsplashPhotoLoadStateAdapter: UnsplashPhotoLoadStateAdapter? = null

    init {
        binding.buttonRetry.setOnClickListener {
            unsplashPhotoLoadStateAdapter?.retry?.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            progressBar.isVisible = loadState is LoadState.Loading
            buttonRetry.isVisible = loadState !is LoadState.Loading
            textViewError.isVisible = loadState !is LoadState.Loading
        }
    }

}