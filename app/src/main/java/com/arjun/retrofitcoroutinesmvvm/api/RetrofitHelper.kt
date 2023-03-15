package com.arjun.retrofitcoroutinesmvvm.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitHelper {

    fun getInstance() : Retrofit {
        return Retrofit.Builder().baseUrl("https://api.quotable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}