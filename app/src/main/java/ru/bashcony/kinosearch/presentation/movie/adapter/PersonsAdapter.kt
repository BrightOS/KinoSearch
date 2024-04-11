package ru.bashcony.kinosearch.presentation.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.data.person.remote.dto.PersonResponse
import ru.bashcony.kinosearch.data.season.remote.dto.SeasonResponse
import ru.bashcony.kinosearch.databinding.ItemPersonBinding
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory
import ru.bashcony.kinosearch.infra.utils.dp
import kotlin.math.roundToInt

class PersonsAdapter(
    private val onPersonClick: (personId: Int) -> Unit
) : PagingDataAdapter<PersonResponse, PersonsAdapter.ViewHolder>(PersonDiffUtil) {

    inner class ViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (getItem(position) ?: PersonResponse()).let {
            Glide.with(holder.binding.personPhoto)
                .load(it.photo)
                .thumbnail(Glide.with(holder.binding.root.context)
                    .load(R.drawable.ic_person)
                    .dontTransform())
                .sizeMultiplier(0.75f)
                .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.binding.personPhoto)

            holder.binding.personCard.setOnClickListener { _ ->
                it.id?.let { onPersonClick(it) }
            }

            holder.binding.personName.text = it.name
            holder.binding.personDescription.text = it.profession?.map { it.value }?.joinToString(", ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object PersonDiffUtil : DiffUtil.ItemCallback<PersonResponse>() {

        override fun areItemsTheSame(oldItem: PersonResponse, newItem: PersonResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PersonResponse, newItem: PersonResponse): Boolean {
            return oldItem == newItem
        }
    }
}