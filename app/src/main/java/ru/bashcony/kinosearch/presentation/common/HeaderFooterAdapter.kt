package ru.bashcony.kinosearch.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.bashcony.kinosearch.databinding.ItemLoadingBinding

class HeaderFooterAdapter : LoadStateAdapter<HeaderFooterAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {

        if (loadState == LoadState.Loading) {
            // show progress viewe
        } else {
            // hide the view
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder =
        ViewHolder(ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    inner class ViewHolder(val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
}