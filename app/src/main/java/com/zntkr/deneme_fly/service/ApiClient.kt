package com.zntkr.deneme_fly.service

import android.util.Log
import com.zntkr.deneme_fly.model.Destination
import com.zntkr.deneme_fly.model.DestinationsList
import com.zntkr.deneme_fly.model.FlyModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class ApiClient(
    private val service: QApi,
    private val serviceDest: DestinationApi,
) {

    suspend fun getAll(page: Int,direction: String?,date: String?) : Response<FlyModel>{
        return service.getData(page = page, flightDirection = direction, scheduleDate = date)
    }

    suspend fun getDestinations(code: String?) : Response<Destination>{
        return serviceDest.getDestinations(code)
    }

}