package com.zntkr.deneme_fly.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val baseUrl = "https://api.schiphol.nl/public-flights/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service : QApi by lazy {
        getInstance().create(QApi::class.java)
    }

    val apiClient = ApiClient(service)
}