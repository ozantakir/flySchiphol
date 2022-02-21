package com.zntkr.deneme_fly.repository

import com.zntkr.deneme_fly.model.Destination
import com.zntkr.deneme_fly.model.DestinationsList
import com.zntkr.deneme_fly.model.FlyModel
import retrofit2.Response

class ApiRepository {

    suspend fun getAll(page: Int,direction: String?,date: String?) : FlyModel? {
        val request = RetrofitHelper.apiClient.getAll(page,direction,date)
        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }

    suspend fun getDestinations(code: String?) : Destination? {
        val req = RetrofitHelper.apiClient.getDestinations(code)
        if(req.isSuccessful){
            return req.body()
        }
        return null
    }


}
