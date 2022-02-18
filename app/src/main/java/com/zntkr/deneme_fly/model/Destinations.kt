package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName

data class Destinations(
    @SerializedName("destinations")
    val destinations: List<Destination>?
)