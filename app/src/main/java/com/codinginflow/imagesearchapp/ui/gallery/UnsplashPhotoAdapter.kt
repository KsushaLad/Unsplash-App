package com.codinginflow.imagesearchapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.codinginflow.imagesearchapp.ui.gallery.adapter.PhotoComparator

import com.codinginflow.imagesearchapp.data.UnsplashPhoto
import com.codinginflow.imagesearchapp.databinding.ItemUnsplashPhotoBinding
import com.codinginflow.imagesearchapp.ui.gallery.adapter.PhotoViewHolder

class UnsplashPhotoAdapter(val listener: OnItemClickListener) :
    PagingDataAdapter<UnsplashPhoto, PhotoViewHolder>(PhotoComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemUnsplashPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let { currentItem ->
            holder.bind(currentItem)
        }
    }


    interface OnItemClickListener {
        fun onItemClick(photo: UnsplashPhoto?)
    }
}