package ru.bashcony.kinosearch.presentation.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.domain.person.entity.PersonEntity
import ru.bashcony.kinosearch.databinding.ItemPersonBinding
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory

class PersonsAdapter(
    private val onPersonClick: (personId: Int) -> Unit
) : PagingDataAdapter<PersonEntity, PersonsAdapter.ViewHolder>(PersonDiffUtil) {

    inner class ViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.binding.personPhoto.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.drawable.ic_person
                )
            )
            if (it.photo != null)
                Glide.with(holder.binding.personPhoto)
                    .load(it.photo)
                    .thumbnail(
                        Glide.with(holder.binding.root.context)
                            .load(R.drawable.ic_person)
                            .dontTransform()
                    )
                    .override(
                        holder.binding.personPhoto.measuredWidth,
                        holder.binding.personPhoto.measuredHeight
                    )
                    .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                    .centerCrop()
                    .into(holder.binding.personPhoto)

            holder.binding.personCard.setOnClickListener { _ ->
                it.id?.let { onPersonClick(it) }
            }

            holder.binding.personName.text = it.name ?: it.enName ?: "Без Ф.И.О."
            holder.binding.personDescription.text =
                it.profession?.map { it.value }?.joinToString(", ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object PersonDiffUtil : DiffUtil.ItemCallback<PersonEntity>() {

        override fun areItemsTheSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
            return oldItem == newItem
        }
    }
}