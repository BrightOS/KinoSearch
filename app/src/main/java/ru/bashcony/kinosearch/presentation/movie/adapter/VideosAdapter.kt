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
import ru.bashcony.kinosearch.domain.movie.entity.VideoEntity
import ru.bashcony.kinosearch.databinding.ItemVideoBinding
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory
import ru.bashcony.kinosearch.infra.utils.parseYouTubeId

class VideosAdapter(
    private val onVideoClick: (videoUrl: String) -> Unit
) : ListAdapter<VideoEntity, VideosAdapter.ViewHolder>(VideoDiffUtil) {

    inner class ViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            if (it.site == "youtube")
                Glide.with(holder.binding.videoRoot)
                    .load("https://img.youtube.com/vi/${parseYouTubeId(it.url.orEmpty())}/0.jpg")
                    .thumbnail(
                        Glide.with(holder.binding.root.context)
                            .load(R.drawable.ic_wallpaper)
                            .dontTransform()
                    )
                    .override(
                        holder.binding.videoPhoto.measuredWidth,
                        holder.binding.videoPhoto.measuredHeight
                    )
                    .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.binding.videoPhoto)

            holder.binding.videoTitle.text = it.name

            holder.binding.videoRoot.setOnClickListener { _ ->
                onVideoClick(it.url.orEmpty())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object VideoDiffUtil : DiffUtil.ItemCallback<VideoEntity>() {
        override fun areItemsTheSame(oldItem: VideoEntity, newItem: VideoEntity) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: VideoEntity, newItem: VideoEntity) =
            oldItem.equals(newItem)

    }
}