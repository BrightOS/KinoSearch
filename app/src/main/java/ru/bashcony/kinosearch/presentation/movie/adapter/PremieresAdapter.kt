package ru.bashcony.kinosearch.presentation.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.bashcony.kinosearch.databinding.ItemPremiereBinding
import ru.bashcony.kinosearch.infra.utils.date
import ru.bashcony.kinosearch.infra.utils.russianStringWithoutHour

class PremieresAdapter : ListAdapter<Pair<String, String>, PremieresAdapter.ViewHolder>(PremiereDiffUtil) {

    inner class ViewHolder(val binding: ItemPremiereBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (getItem(position) ?: Pair("", "")).let {
            holder.binding.premiereTitle.text = it.first
            holder.binding.premiereName.text = it.second.date.russianStringWithoutHour
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemPremiereBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    object PremiereDiffUtil : DiffUtil.ItemCallback<Pair<String, String>>() {

        override fun areItemsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
            return oldItem.first.equals(newItem.first)
        }

        override fun areContentsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
            return oldItem.equals(newItem)
        }
    }
}