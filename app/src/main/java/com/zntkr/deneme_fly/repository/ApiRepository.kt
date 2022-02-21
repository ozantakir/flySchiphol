package com.zntkr.deneme_fly.repository

import com.zntkr.deneme_fly.model.FlyModel
import com.zntkr.deneme_fly.service.RetrofitHelper

class ApiRepository {

    suspend fun getAll(page: Int,direction: String?,date: String?) : FlyModel? {
        val request = RetrofitHelper.apiClient.getAll(page,direction,date)
        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }
}
