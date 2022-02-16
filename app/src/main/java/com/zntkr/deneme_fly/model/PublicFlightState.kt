package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName

data class PublicFlightState(
    @SerializedName("flightStates")
    val flightStates: List<String>?
)