package ru.bashcony.kinosearch.presentation.common.epoxy

import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.presentation.common.utils.KotlinHolder

@EpoxyModelClass
abstract class VideoEpoxyModel : EpoxyModelWithHolder<VideoEpoxyModel.Holder>(){

    @EpoxyAttribute
    var videoTitle = ""

    @EpoxyAttribute
    var videoPhoto = ""

    @EpoxyAttribute
    var videoUrl = ""

    @EpoxyAttribute
    var onVideoClick: (String) -> Unit = {}

    override fun getDefaultLayout() = R.layout.item_video

    override fun bind(holder: Holder) {
        holder.videoRoot.setOnClickListener {
            onVideoClick(videoUrl)
        }

        holder.videoTitle.text = videoTitle

        Glide.with(holder.videoPhoto.context)
            .load(videoPhoto)
            .into(holder.videoPhoto)
    }

    inner class Holder: KotlinHolder() {
        val videoRoot: MaterialCardView by bind(R.id.video_root)
        val videoTitle: TextView by bind(R.id.video_title)
        val videoPhoto: ImageView by bind(R.id.video_photo)
    }
}