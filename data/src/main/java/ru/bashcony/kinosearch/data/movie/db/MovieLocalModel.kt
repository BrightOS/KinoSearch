package ru.bashcony.kinosearch.data.movie.db

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("movie_table")
data class MovieLocalModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("cover_url") val coverUrl: String? = "",
    @SerializedName("rating") val rating: Double? = 0.0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("alt_name") val altName: String? = "",
    @SerializedName("type") val type: String? = "",
    @SerializedName("years_from") val yearsFrom: String? = "",
    @SerializedName("years_to") val yearsTo: String? = ""
)