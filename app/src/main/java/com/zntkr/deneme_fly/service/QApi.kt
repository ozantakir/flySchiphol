package com.zntkr.deneme_fly.service

import com.zntkr.deneme_fly.model.Destination
import com.zntkr.deneme_fly.model.FlyModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

// main data
interface QApi {
    @Headers(
        "Accept: application/json",
        "app_id: 9dc89eba",
        "app_key: 9524bd2d114e102a226e88ee056c270b",
        "ResourceVersion: v4"
    )
    @GET("flights")
    suspend fun getData(
        @Query("page") page:Int,
        @Query("flightDirection") flightDirection:String? = null,
        @Query("scheduleDate") scheduleDate:String? = null,
    ) : Response<FlyModel>
}

// to get destination as country and city
interface DestinationApi {
    @Headers(
        "Accept: application/json",
        "app_id: 9dc89eba",
        "app_key: 9524bd2d114e102a226e88ee056c270b",
        "ResourceVersion: v4"
    )
    @GET("destinations/{iata}")
    suspend fun getDestinations(
        @Path("iata") code:String?
    ) : Response<Destination>
}


