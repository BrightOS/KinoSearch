package ru.bashcony.kinosearch.presentation.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonResponse
import ru.bashcony.kinosearch.databinding.ItemGenreBinding
import ru.bashcony.kinosearch.databinding.ItemSeasonBinding

class GenresAdapter(
    private val onGenreClick: (genreName: String) -> Unit
) : ListAdapter<String, GenresAdapter.ViewHolder>(SeasonDiffUtil) {

    inner class ViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (getItem(position) ?: "").let {
            holder.binding.genreRoot.text = it
            holder.binding.genreRoot.setOnClickListener { _ ->
                onGenreClick(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object SeasonDiffUtil : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.equals(newItem)
        }
    }
}