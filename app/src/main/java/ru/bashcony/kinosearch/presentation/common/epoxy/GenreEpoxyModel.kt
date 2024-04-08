package ru.bashcony.kinosearch.presentation.common.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.presentation.common.utils.KotlinHolder

@EpoxyModelClass
abstract class GenreEpoxyModel : EpoxyModelWithHolder<GenreEpoxyModel.Holder>() {

    @EpoxyAttribute
    var genre = "..."

    @EpoxyAttribute
    var onGenreClick: (String) -> Unit = {}

    override fun getDefaultLayout() = R.layout.item_genre

    override fun bind(holder: Holder) {
        holder.genreRoot.text = genre
        holder.genreRoot.setOnClickListener {
            onGenreClick(genre)
        }
    }

    inner class Holder : KotlinHolder() {
        val genreRoot: TextView by bind(R.id.genre_root)
    }
}