package ru.bashcony.kinosearch.data.person.remote.dto

import com.google.gson.annotations.SerializedName

data class ProfessionResponse(
    @SerializedName("value") val value: String?
)
