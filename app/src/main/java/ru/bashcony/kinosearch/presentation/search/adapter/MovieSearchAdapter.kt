package ru.bashcony.kinosearch.presentation.search.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.databinding.ItemSearchMovieBinding
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory
import ru.bashcony.kinosearch.infra.utils.typeToRussian
import java.util.Locale

class MovieSearchAdapter(
    private val onMovieClick: (movieEntity: MovieEntity) -> Unit
) : PagingDataAdapter<MovieEntity, MovieSearchAdapter.ViewHolder>(MovieDiffUtil) {

    inner class ViewHolder(val binding: ItemSearchMovieBinding) :
        RecyclerView.ViewHolder(binding.root)


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.movieCover.setImageBitmap(null)

        getItem(position)?.let {
            if (it.poster?.previewUrl != null)
                Glide.with(holder.binding.movieCover)
                    .load(it.poster?.previewUrl)
                    .override(
                        holder.binding.movieCover.measuredWidth,
                        holder.binding.movieCover.measuredHeight
                    )
                    .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                    .centerCrop()
                    .into(holder.binding.movieCover)

            holder.binding.movieType.text = typeToRussian[it.type] ?: "другое"
            holder.binding.movieTitle.text = if (it.name.isNullOrBlank().not())
                it.name
            else if (it.alternativeName.isNullOrBlank().not())
                it.alternativeName
            else
                it.enName
            holder.binding.movieRating.apply {
                val rating = it.rating?.kp ?: 0.0
                if (it.rating?.kp != null && rating > 0.1) {
                    visibility = View.VISIBLE
                    backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context, when {
                                rating < 5.0 -> R.color.color_bad
                                rating < 7.0 -> R.color.color_medium
                                else -> R.color.color_good
                            }
                        )
                    )
                    text = String.format(Locale.ENGLISH, "%.1f", rating)
                } else
                    visibility = View.GONE
            }

            holder.binding.movieRoot.setOnClickListener { _ ->
                onMovieClick(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemSearchMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    object MovieDiffUtil : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem.equals(newItem)

    }
}