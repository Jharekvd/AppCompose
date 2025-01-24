package com.vargas.androidjcapi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Objeto singleton que proporciona una instancia de Retrofit configurada.
object RetrofitServiceFactory {
    // Propiedad lazy que inicializa Retrofit solo cuando se accede a el por primera vez
    private val retrofit by lazy {
        //Contrui vacio de retrofit
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/") // URL base de la API.
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor para Json para que se pueda leer y mandar nuestra consulta
            .build() // Construcción del cliente Retrofit.
    }

    // Propiedad lazy que crea el servicio API usando la interfaz NewsApiService.
    val api: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}