package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName

data class AircraftType(
    @SerializedName("iataMain")
    val iataMain: String?,
    @SerializedName("iataSub")
    val iataSub: String?
)