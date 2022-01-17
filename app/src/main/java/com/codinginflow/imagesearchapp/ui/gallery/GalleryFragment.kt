package com.codinginflow.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.data.UnsplashPhoto
import com.codinginflow.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.codinginflow.imagesearchapp.enums.QueryForSearch


@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery),
    UnsplashPhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding
    private lateinit var textChangeCountDownJob: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)
        val adapter = UnsplashPhotoAdapter(this)
        bindingGalleryFragment(adapter)
        observe(adapter)
        load(adapter)
        setHasOptionsMenu(true)
        buttonsCategory()
    }

    private fun bindingGalleryFragment(unsplashPhotoAdapter: UnsplashPhotoAdapter) {
        binding?.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = unsplashPhotoAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { unsplashPhotoAdapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { unsplashPhotoAdapter.retry() }
            )
            buttonRetry.setOnClickListener { unsplashPhotoAdapter.retry() }
        }
    }


    private fun load(unsplashPhotoAdapter: UnsplashPhotoAdapter) {
        unsplashPhotoAdapter.addLoadStateListener { loadState ->
            binding?.apply {
                this.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                this.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
                val isRefresh = loadState.source.refresh is LoadState.NotLoading &&
                                    loadState.append.endOfPaginationReached &&
                                    unsplashPhotoAdapter.itemCount < 1
                recyclerView.isVisible = !isRefresh
                                textViewEmpty.isVisible = isRefresh
            }
        }
    }

    private fun buttonsCategory() {
        binding?.nature?.setOnClickListener {
            binding?.recyclerView?.scrollToPosition(0)
            viewModel.searchPhotos(QueryForSearch.D3.name)
        }

        binding?.bus?.setOnClickListener {
            binding?.recyclerView?.scrollToPosition(0)
            viewModel.searchPhotos(QueryForSearch.TEXTURES.name)
        }

        binding?.car?.setOnClickListener {
            binding?.recyclerView?.scrollToPosition(0)
            viewModel.searchPhotos(QueryForSearch.NATURE.name)
        }

        binding?.train?.setOnClickListener {
            binding?.recyclerView?.scrollToPosition(0)
            viewModel.searchPhotos(QueryForSearch.FOOD.name)
        }

        binding?.trending?.setOnClickListener {
            binding?.recyclerView?.scrollToPosition(0)
            viewModel.searchPhotos(QueryForSearch.TRAVEL.name)
        }

        binding?.animals?.setOnClickListener {
            binding?.recyclerView?.scrollToPosition(0)
            viewModel.searchPhotos(QueryForSearch.ANIMALS.name)
        }
    }

    private fun observe(unsplashPhotoAdapter: UnsplashPhotoAdapter) {
        viewModel.photos.observe(viewLifecycleOwner) {
            unsplashPhotoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onItemClick(photo: UnsplashPhoto?) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo!!)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (::textChangeCountDownJob.isInitialized)
                    textChangeCountDownJob.cancel()
                    textChangeCountDownJob = lifecycleScope.launch {
                    delay(1500)
                    if (query != null) {
                        viewModel.searchPhotos(query)
                        searchView.clearFocus()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (::textChangeCountDownJob.isInitialized)
                    textChangeCountDownJob.cancel()
                textChangeCountDownJob = lifecycleScope.launch {
                    delay(1500)
                    if (newText != null) {
                        if (newText.isEmpty()) {
                            viewModel.searchPhotos("new")
                            searchView.clearFocus()
                        }
                    }
                }
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}