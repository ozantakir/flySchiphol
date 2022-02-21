package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("destinations")
    var destinations: List<String>?,
    @SerializedName("eu")
    val eu: String?,
    @SerializedName("visa")
    val visa: Boolean?
)