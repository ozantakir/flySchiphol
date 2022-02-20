package com.zntkr.deneme_fly.service

import com.zntkr.deneme_fly.model.FlyModel
import retrofit2.Response

class ApiClient(
    private val service: QApi
) {

    suspend fun getAll(page: Int,direction: String?,date: String?) : Response<FlyModel>{
        return service.getData(page = page, flightDirection = direction, scheduleDate = date)
    }
}