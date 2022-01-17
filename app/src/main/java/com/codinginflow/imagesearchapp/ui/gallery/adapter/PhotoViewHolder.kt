package com.codinginflow.imagesearchapp.ui.gallery.adapter

import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.imagesearchapp.data.UnsplashPhoto
import com.codinginflow.imagesearchapp.databinding.ItemUnsplashPhotoBinding
import com.codinginflow.imagesearchapp.extensions.loading

import com.codinginflow.imagesearchapp.ui.gallery.UnsplashPhotoAdapter

class PhotoViewHolder(
    private val binding: ItemUnsplashPhotoBinding,
    val listener: UnsplashPhotoAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: UnsplashPhoto) {
        binding.apply {
            imgImagePreview.loading(photo)
            textViewUserName.text = photo.user.username
            root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(photo)
                }
            }
        }
    }
}