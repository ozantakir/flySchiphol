package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName

data class Destination(
    @SerializedName("city")
    val city: Any?,
    @SerializedName("country")
    val country: String?,

)