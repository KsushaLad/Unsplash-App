package com.codinginflow.imagesearchapp.ui.details

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*
import java.io.IOException
import com.codinginflow.imagesearchapp.scale.MyScaleGestures
import com.codinginflow.imagesearchapp.R.string.*
import com.codinginflow.imagesearchapp.data.UnsplashPhoto
import com.codinginflow.imagesearchapp.extensions.loading


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailsFragmentArgs>()


    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)
        val wallpaperManager = WallpaperManager.getInstance(requireContext())
        binding.apply {
            val photo = args.photo
            loadingAllInDetailFragment(photo)
            imgImagePreview.setOnTouchListener(MyScaleGestures(requireContext()))
            setWallpaper(wallpaperManager)
            val uri = Uri.parse(photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            createLinkToUnsplash(intent)
        }
    }

    private fun loadingAllInDetailFragment(photo: UnsplashPhoto) {
        imgImagePreview.loading(photo)
        text_view_creator.isVisible = true
        progress_bar.isVisible = false
    }

    private fun setWallpaper(wallpaperManager: WallpaperManager) {
        set.setOnClickListener { view ->
            Toast.makeText(context, done, Toast.LENGTH_SHORT).show()
            val bitmap = (imgImagePreview.drawable as BitmapDrawable).bitmap
            try {
                wallpaperManager.setBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    private fun createLinkToUnsplash(intent: Intent) {
        text_view_creator.apply {
            text = getString(photo_by_on_Unsplash).plus(" ").plus(args.photo.user.name).plus(" ")
                .plus(getString(photo_by_on_Unsplash1))
            setOnClickListener {
                context.startActivity(intent)
            }
            paint.isUnderlineText = true
        }
    }
}




