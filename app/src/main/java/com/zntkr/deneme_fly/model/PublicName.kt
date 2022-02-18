package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName

data class PublicName(
    @SerializedName("dutch")
    val dutch: String?,
    @SerializedName("english")
    val english: String?
)