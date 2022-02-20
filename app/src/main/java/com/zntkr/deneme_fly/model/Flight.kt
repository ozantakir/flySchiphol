package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Flight(
    @SerializedName("flightDirection")
    val flightDirection: String?,
    @SerializedName("flightName")
    val flightName: String?,
    @SerializedName("flightNumber")
    val flightNumber: Int?,
    @SerializedName("gate")
    val gate: Any?,
    @SerializedName("route")
    val route: Route?,
    @SerializedName("scheduleDate")
    val scheduleDate: String?,
    @SerializedName("scheduleDateTime")
    val scheduleDateTime: String?,
    @SerializedName("scheduleTime")
    val scheduleTime: String?,
) : Serializable