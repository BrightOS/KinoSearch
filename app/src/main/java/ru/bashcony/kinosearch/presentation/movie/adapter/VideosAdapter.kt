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
import ru.bashcony.kinosearch.data.movie.remote.dto.TrailersResponse
import ru.bashcony.kinosearch.data.movie.remote.dto.VideoResponse
import ru.bashcony.kinosearch.databinding.ItemSimilarMovieBinding
import ru.bashcony.kinosearch.databinding.ItemVideoBinding
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory
import ru.bashcony.kinosearch.infra.utils.parseYouTubeId
import ru.bashcony.kinosearch.infra.utils.typeToRussian

class VideosAdapter(
    private val onVideoClick: (videoUrl: String) -> Unit
) : ListAdapter<VideoResponse, VideosAdapter.ViewHolder>(VideoDiffUtil) {

    inner class ViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (getItem(position) ?: VideoResponse()).let {
            if (it.site == "youtube")
                Glide.with(holder.binding.videoRoot)
                    .load("https://img.youtube.com/vi/${parseYouTubeId(it.url.orEmpty())}/0.jpg")
                    .thumbnail(
                        Glide.with(holder.binding.root.context)
                            .load(R.drawable.ic_wallpaper)
                            .dontTransform()
                    )
                    .sizeMultiplier(0.75f)
                    .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(holder.binding.videoPhoto)

            holder.binding.videoTitle.text = it.name

            holder.binding.videoRoot.setOnClickListener { _ ->
                onVideoClick(it.url.orEmpty())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object VideoDiffUtil : DiffUtil.ItemCallback<VideoResponse>() {
        override fun areItemsTheSame(oldItem: VideoResponse, newItem: VideoResponse) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: VideoResponse, newItem: VideoResponse) =
            oldItem.equals(newItem)

    }
}