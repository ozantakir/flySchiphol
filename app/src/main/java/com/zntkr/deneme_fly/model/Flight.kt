package com.zntkr.deneme_fly.model


import com.google.gson.annotations.SerializedName

data class Flight(
    @SerializedName("actualLandingTime")
    val actualLandingTime: String?,
    @SerializedName("actualOffBlockTime")
    val actualOffBlockTime: Any?,
    @SerializedName("aircraftRegistration")
    val aircraftRegistration: String?,
    @SerializedName("aircraftType")
    val aircraftType: AircraftType?,
    @SerializedName("airlineCode")
    val airlineCode: Int?,
    @SerializedName("baggageClaim")
    val baggageClaim: BaggageClaim?,
    @SerializedName("checkinAllocations")
    val checkinAllocations: Any?,
    @SerializedName("codeshares")
    val codeshares: Any?,
    @SerializedName("estimatedLandingTime")
    val estimatedLandingTime: String?,
    @SerializedName("expectedSecurityFilter")
    val expectedSecurityFilter: Any?,
    @SerializedName("expectedTimeBoarding")
    val expectedTimeBoarding: Any?,
    @SerializedName("expectedTimeGateClosing")
    val expectedTimeGateClosing: Any?,
    @SerializedName("expectedTimeGateOpen")
    val expectedTimeGateOpen: Any?,
    @SerializedName("expectedTimeOnBelt")
    val expectedTimeOnBelt: String?,
    @SerializedName("flightDirection")
    val flightDirection: String?,
    @SerializedName("flightName")
    val flightName: String?,
    @SerializedName("flightNumber")
    val flightNumber: Int?,
    @SerializedName("gate")
    val gate: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("isOperationalFlight")
    val isOperationalFlight: Boolean?,
    @SerializedName("lastUpdatedAt")
    val lastUpdatedAt: String?,
    @SerializedName("mainFlight")
    val mainFlight: String?,
    @SerializedName("pier")
    val pier: String?,
    @SerializedName("prefixIATA")
    val prefixIATA: String?,
    @SerializedName("prefixICAO")
    val prefixICAO: String?,
    @SerializedName("publicEstimatedOffBlockTime")
    val publicEstimatedOffBlockTime: Any?,
    @SerializedName("publicFlightState")
    val publicFlightState: PublicFlightState?,
    @SerializedName("route")
    val route: Route?,
    @SerializedName("scheduleDate")
    val scheduleDate: String?,
    @SerializedName("scheduleDateTime")
    val scheduleDateTime: String?,
    @SerializedName("scheduleTime")
    val scheduleTime: String?,
    @SerializedName("schemaVersion")
    val schemaVersion: String?,
    @SerializedName("serviceType")
    val serviceType: String?,
    @SerializedName("terminal")
    val terminal: Int?,
    @SerializedName("transferPositions")
    val transferPositions: Any?
)