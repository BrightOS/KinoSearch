package ru.bashcony.kinosearch.presentation.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonResponse
import ru.bashcony.kinosearch.databinding.ItemSeasonBinding

class SeasonsAdapter(
    private val onSeasonClick: (seasonNumber: Int) -> Unit
) : PagingDataAdapter<SeasonResponse, SeasonsAdapter.ViewHolder>(SeasonDiffUtil) {

    inner class ViewHolder(val binding: ItemSeasonBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(getItem(position) ?: SeasonResponse()) {
            Glide.with(holder.binding.seasonRoot.context)
                .load(poster?.previewUrl)
                .into(holder.binding.seasonCover)

            holder.binding.seasonNumber.text = if ((number ?: 0) > 0) "Сезон $number" else "Спешлы"
            holder.binding.seasonEpisodes.text = "${episodes?.count()} серии"

            holder.binding.seasonRoot.setOnClickListener {
                number?.let { onSeasonClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object SeasonDiffUtil : DiffUtil.ItemCallback<SeasonResponse>() {

        override fun areItemsTheSame(oldItem: SeasonResponse, newItem: SeasonResponse): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: SeasonResponse, newItem: SeasonResponse): Boolean {
            return oldItem == newItem
        }
    }
}