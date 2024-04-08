package ru.bashcony.kinosearch.presentation.common.epoxy

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.presentation.common.utils.KotlinHolder

@EpoxyModelClass
abstract class ReviewEpoxyModel : EpoxyModelWithHolder<ReviewEpoxyModel.Holder>() {

    @EpoxyAttribute
    var reviewId = -1

    @EpoxyAttribute
    var reviewName = ""

    @EpoxyAttribute
    var reviewDate = ""

    @EpoxyAttribute
    var reviewDescription = ""

    @EpoxyAttribute
    var reviewType = ""

    @EpoxyAttribute
    var reviewPhoto = ""

    @EpoxyAttribute
    var onReviewClick: (reviewId: Int) -> Unit = {}

    override fun getDefaultLayout() = R.layout.item_review

    override fun bind(holder: Holder) {

        Glide.with(holder.reviewPhoto)
            .load(reviewPhoto)
            .into(holder.reviewPhoto)

        holder.reviewColor.background = ColorDrawable(
            when (reviewType) {
                "Негативный" -> 0xFF0000
                "Позитивный" -> 0x00FF00
                else -> 0x888888
            }
        )

        holder.reviewName.text = reviewName
        holder.reviewDate.text = reviewDate
        holder.reviewDescription.text = reviewDescription
        holder.reviewRoot.setOnClickListener {
            onReviewClick(reviewId)
        }
    }

    inner class Holder : KotlinHolder() {
        val reviewColor: View by bind(R.id.review_color)
        val reviewDate: TextView by bind(R.id.review_date)
        val reviewDescription: TextView by bind(R.id.review_description)
        val reviewName: TextView by bind(R.id.review_name)
        val reviewPhoto: ShapeableImageView by bind(R.id.review_photo)
        val reviewRoot: MaterialCardView by bind(R.id.review_card)
    }
}