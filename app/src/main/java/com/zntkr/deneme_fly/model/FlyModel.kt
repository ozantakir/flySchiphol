package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlyModel(
    @SerializedName("flights")
    val flights: List<Flight>?
) :Serializable