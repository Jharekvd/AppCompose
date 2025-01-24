package com.vargas.androidjcapi.Data

// Clase que va almacenar la respuesta de la api de https://newsapi.org/.
// Contiene el estado de la solicitud, el número total de resultados y la lista de artículos obtenidos.
data class NewsApiResponse(
    val status: String,           // Estado de la respuesta de la API.
    val totalResults: Int,        // Número total de artículos disponibles en la api.
    val articles: List<Article>   // Lista de articulos obtenidos de la api
)
