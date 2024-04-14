package ru.bashcony.kinosearch.presentation.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.bashcony.kinosearch.databinding.ItemGenrePickerBinding
import ru.bashcony.kinosearch.domain.movie.entity.ValueEntity

class GenresAdapter(
    private val onGenreCheckChanged: (genre: ValueEntity, isChecked: Boolean) -> Unit
) : ListAdapter<ValueEntity, GenresAdapter.ViewHolder>(GenreDiffUtil) {

    inner class ViewHolder(val binding: ItemGenrePickerBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.binding.root.text = it.name
            holder.binding.root.setOnCheckedChangeListener { buttonView, isChecked ->
                onGenreCheckChanged(it, isChecked)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemGenrePickerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object GenreDiffUtil : DiffUtil.ItemCallback<ValueEntity>() {

        override fun areItemsTheSame(oldItem: ValueEntity, newItem: ValueEntity): Boolean {
            return oldItem.name.equals(newItem.name)
        }

        override fun areContentsTheSame(oldItem: ValueEntity, newItem: ValueEntity): Boolean {
            return oldItem.equals(newItem)
        }
    }
}