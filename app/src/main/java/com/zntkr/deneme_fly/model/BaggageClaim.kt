package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName

data class BaggageClaim(
    @SerializedName("belts")
    val belts: List<String>?
)