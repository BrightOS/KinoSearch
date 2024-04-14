package ru.bashcony.kinosearch.presentation.movie.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.domain.image.entity.ImageEntity
import ru.bashcony.kinosearch.databinding.ItemImageBinding
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory

class ImagesAdapter(
    private val onImageClick: (imageUrl: String) -> Unit
) : PagingDataAdapter<ImageEntity, ImagesAdapter.ViewHolder>(ImageDiffUtil) {

    inner class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            if (it.url != null)
                Glide.with(holder.binding.root.context)
                    .load(it.previewUrl ?: it.url)
                    .thumbnail(
                        Glide.with(holder.binding.root.context)
                            .load(R.drawable.ic_person)
                            .dontTransform()
                    )
                    .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                    .into(holder.binding.image)

            holder.binding.image.setOnClickListener { _ ->
                it.url?.let { onImageClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object ImageDiffUtil : DiffUtil.ItemCallback<ImageEntity>() {

        override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
            return oldItem == newItem
        }
    }
}