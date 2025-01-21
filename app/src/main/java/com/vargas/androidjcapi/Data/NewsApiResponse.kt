package com.vargas.androidjcapi.Data

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)