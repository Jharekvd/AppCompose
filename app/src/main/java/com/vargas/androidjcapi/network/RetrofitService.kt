package com.vargas.androidjcapi.network

import com.vargas.androidjcapi.Data.NewsApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): NewsApiResponse
}
