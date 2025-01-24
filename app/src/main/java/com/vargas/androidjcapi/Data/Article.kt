package com.vargas.androidjcapi.Data

// Clase articulo para almacenar la informacion traida de la api https://newsapi.org/
data class Article(
    val title: String,       // Título del artículo.
    val description: String, // Descripción breve del artículo.
    val url: String,         // Enlace directo al artículo.
    val urlToImage: String?  // Enlace a la imagen del artículo (opcional).
)
