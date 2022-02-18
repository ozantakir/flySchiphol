package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName

data class Destination(
    @SerializedName("city")
    val city: Any?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("iata")
    val iata: String?,
    @SerializedName("publicName")
    val publicName: PublicName?
)