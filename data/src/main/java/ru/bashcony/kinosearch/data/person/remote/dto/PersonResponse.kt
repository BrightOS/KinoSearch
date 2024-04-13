package ru.bashcony.kinosearch.data.person.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PersonResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("age") val age: Int? = null,
    @SerializedName("enName") val enName: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("photo") val photo: String? = null,
    @SerializedName("sex") val sex: String? = null,
    @SerializedName("profession") val profession: List<ProfessionResponse>? = listOf()
)
