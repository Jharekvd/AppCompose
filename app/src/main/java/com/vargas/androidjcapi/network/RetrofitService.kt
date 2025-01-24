package com.vargas.androidjcapi.network

import com.vargas.androidjcapi.Data.NewsApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Interfaz que define el servicio de API para obtener noticias usando Retrofit.
interface NewsApiService {

    // Método GET para obtener noticias relacionadas con un tema específico.
    @GET("v2/everything")
    //Metodo suspend , permite ejecutarse dentro de una corrunit (asincrona)
    suspend fun getNews(
        @Query("q") query: String,       // Parámetro "q" de la consulta (palabra clave para buscar noticias).
        @Query("apiKey") apiKey: String // Parámetro "apiKey" para autenticar la solicitud.
    ): NewsApiResponse // Devuelve la respuesta como un objeto de NewsApiResponse.
}
