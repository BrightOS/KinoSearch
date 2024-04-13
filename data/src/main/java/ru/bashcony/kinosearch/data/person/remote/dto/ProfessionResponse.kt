package ru.bashcony.kinosearch.data.person.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfessionResponse(
    @SerializedName("value") val value: String?
)
