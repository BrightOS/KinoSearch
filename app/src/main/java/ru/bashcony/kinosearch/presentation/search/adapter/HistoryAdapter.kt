package ru.bashcony.kinosearch.presentation.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.bashcony.kinosearch.databinding.ItemGenrePickerBinding
import ru.bashcony.kinosearch.databinding.ItemHistoryBinding
import ru.bashcony.kinosearch.domain.movie.entity.MovieEntity
import ru.bashcony.kinosearch.domain.movie.entity.ValueEntity
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory

class HistoryAdapter(
    private val onMovieClick: (movieEntity: MovieEntity) -> Unit,
    private val onDeleteClick: (movieEntity: MovieEntity) -> Unit
) : ListAdapter<MovieEntity, HistoryAdapter.ViewHolder>(MovieDiffUtil) {

    inner class ViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.historyCover.setImageBitmap(null)

        getItem(position)?.let {
            holder.binding.historyTitle.text = it.name ?: it.alternativeName
            if (it.poster?.previewUrl != null)
                Glide.with(holder.binding.historyCover)
                    .load(it.poster?.previewUrl)
                    .override(
                        holder.binding.historyCover.measuredWidth,
                        holder.binding.historyCover.measuredHeight
                    )
                    .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                    .centerCrop()
                    .into(holder.binding.historyCover)

            holder.binding.historySubtitle.text = "${it.type}, ${if (it.name != null) it.alternativeName else ""}"

            holder.binding.root.setOnClickListener { _ ->
                onMovieClick(it)
            }

            holder.binding.historyCover.setOnClickListener { _ ->
                onMovieClick(it)
            }

            holder.binding.historyTitle.setOnClickListener { _ ->
                onMovieClick(it)
            }

            holder.binding.historySubtitle.setOnClickListener { _ ->
                onMovieClick(it)
            }

            holder.binding.historyDelete.setOnClickListener { _ ->
                onDeleteClick(it)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object MovieDiffUtil : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem.equals(newItem)

    }
}