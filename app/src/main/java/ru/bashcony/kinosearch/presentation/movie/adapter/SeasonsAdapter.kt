package ru.bashcony.kinosearch.presentation.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import ru.bashcony.kinosearch.domain.season.entity.SeasonEntity
import ru.bashcony.kinosearch.databinding.ItemSeasonBinding
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory

class SeasonsAdapter(
    private val onSeasonClick: (seasonNumber: Int) -> Unit
) : PagingDataAdapter<SeasonEntity, SeasonsAdapter.ViewHolder>(SeasonDiffUtil) {

    inner class ViewHolder(val binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let{
            if (it.poster?.previewUrl != null)
                Glide.with(holder.binding.seasonRoot.context)
                    .load(it.poster?.previewUrl)
                    .apply(
                        RequestOptions().centerCrop()
                        .override(
                            holder.binding.seasonCover.measuredWidth,
                            holder.binding.seasonCover.measuredHeight
                        ))
                    .transition(
                        DrawableTransitionOptions.with(
                            DrawableAlwaysCrossFadeFactory()
                        ))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.seasonCover)

            holder.binding.seasonNumber.text = if ((it.number ?: 0) > 0) "Сезон ${it.number}" else "Спешлы"
            holder.binding.seasonEpisodes.text = "${it.episodes?.count()} серии"

            holder.binding.seasonRoot.setOnClickListener { _ ->
                it.number?.let { onSeasonClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object SeasonDiffUtil : DiffUtil.ItemCallback<SeasonEntity>() {

        override fun areItemsTheSame(oldItem: SeasonEntity, newItem: SeasonEntity): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: SeasonEntity, newItem: SeasonEntity): Boolean {
            return oldItem == newItem
        }
    }
}