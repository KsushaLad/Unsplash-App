package com.codinginflow.imagesearchapp.ui.gallery.adapter

import androidx.recyclerview.widget.DiffUtil
import com.codinginflow.imagesearchapp.data.UnsplashPhoto


class PhotoComparator : DiffUtil.ItemCallback<UnsplashPhoto>() {

    override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto) =
        oldItem == newItem

}