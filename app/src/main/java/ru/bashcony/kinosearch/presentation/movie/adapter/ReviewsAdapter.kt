package ru.bashcony.kinosearch.presentation.movie.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.data.review.remote.dto.ReviewResponse
import ru.bashcony.kinosearch.databinding.ItemReviewBinding
import ru.bashcony.kinosearch.infra.utils.date
import ru.bashcony.kinosearch.infra.utils.russianStringWithHour

class ReviewsAdapter(
    private val onReviewClick: (reviewResponse: ReviewResponse) -> Unit
) : PagingDataAdapter<ReviewResponse, ReviewsAdapter.ViewHolder>(ReviewDiffUtil) {

    inner class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (getItem(position) ?: ReviewResponse()).let {
            Glide.with(holder.binding.root.context)
                .load(R.drawable.ic_person)
                .into(holder.binding.reviewPhoto)

            holder.binding.reviewName.text = it.author
            holder.binding.reviewTitle.text = it.title
            holder.binding.reviewDescription.text = it.review
            holder.binding.reviewDate.text = it.date?.date?.russianStringWithHour

            holder.binding.reviewColor.background = ColorDrawable(
                ContextCompat.getColor(
                    holder.binding.reviewCard.context,
                    when (it.type) {
                        "Негативный" -> R.color.color_bad
                        "Позитивный" -> R.color.color_good
                        else -> R.color.color_medium
                    }
                )
            )

            holder.binding.reviewCard.setOnClickListener { _ ->
                onReviewClick(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object ReviewDiffUtil : DiffUtil.ItemCallback<ReviewResponse>() {

        override fun areItemsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReviewResponse, newItem: ReviewResponse): Boolean {
            return oldItem == newItem
        }
    }
}