package ru.bashcony.kinosearch.presentation.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.data.movie.remote.dto.LinkedMovieResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.MovieResponse
import ru.bashcony.kinosearch.databinding.ItemSimilarMovieBinding
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory
import ru.bashcony.kinosearch.infra.utils.typeToRussian

class SimilarMoviesAdapter(
    private val onMovieClick: (movieId: Int) -> Unit
) : ListAdapter<LinkedMovieResponse, SimilarMoviesAdapter.ViewHolder>(MovieDiffUtil) {

    inner class ViewHolder(val binding: ItemSimilarMovieBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (getItem(position) ?: LinkedMovieResponse()).let {
            Glide.with(holder.binding.movieCover)
                .load(it.poster?.previewUrl)
                .thumbnail(Glide.with(holder.binding.root.context)
                    .load(R.drawable.ic_wallpaper)
                    .dontTransform())
                .sizeMultiplier(0.75f)
                .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.binding.movieCover)

            holder.binding.movieType.text = typeToRussian[it.type] ?: "другое"
            holder.binding.movieTitle.text = it.name

            holder.binding.movieRoot.setOnClickListener { _ ->
                onMovieClick(it.id ?: -1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemSimilarMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object MovieDiffUtil : DiffUtil.ItemCallback<LinkedMovieResponse>() {
        override fun areItemsTheSame(oldItem: LinkedMovieResponse, newItem: LinkedMovieResponse) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LinkedMovieResponse, newItem: LinkedMovieResponse) =
            oldItem.equals(newItem)

    }
}