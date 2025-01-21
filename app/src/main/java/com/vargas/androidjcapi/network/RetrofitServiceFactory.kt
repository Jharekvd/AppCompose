package com.vargas.androidjcapi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitServiceFactory {
    object RetrofitServiceFactory {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: NewsApiService by lazy {
            retrofit.create(NewsApiService::class.java)
        }
    }
}