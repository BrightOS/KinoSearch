package ru.bashcony.kinosearch.presentation.common.epoxy

import android.view.ViewParent
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
abstract class PersonEpoxyModel : EpoxyModelWithHolder<PersonEpoxyModel.Holder>() {

    @EpoxyAttribute
    var personName = ""

    @EpoxyAttribute
    var personDescription = ""

    @EpoxyAttribute
    var personPhoto = ""

    override fun getDefaultLayout() = R.layout.item_person

    override fun bind(holder: Holder) {
        Glide.with(holder.personRoot.context)
            .load(personPhoto)
            .into(holder.personPhoto)

        holder.personName.text = personName
        holder.personDescription.text = personDescription
    }

    inner class Holder: KotlinHolder() {
        val personRoot: MaterialCardView by bind(R.id.person_card)
        val personPhoto: ShapeableImageView by bind(R.id.person_photo)
        val personName: TextView by bind(R.id.person_name)
        val personDescription: TextView by bind(R.id.person_description)
    }
}