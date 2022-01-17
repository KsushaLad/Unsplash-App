package com.codinginflow.imagesearchapp.extensions


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.data.UnsplashPhoto
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.unsplash_photo_load_state_footer.view.*


fun ImageView.loading(photo: UnsplashPhoto) {
    Glide.with(this@loading)
        .load(photo.urls.full)
        .error(R.drawable.ic_error)
        .into(imgImagePreview)
}
