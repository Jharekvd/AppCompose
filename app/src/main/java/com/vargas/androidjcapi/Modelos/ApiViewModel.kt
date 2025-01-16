package com.vargas.androidjcapi.Modelos

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vargas.androidjcapi.network.Article
import com.vargas.androidjcapi.network.RetrofitServiceFactory
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {
    private val _news = mutableStateOf<List<Article>>(emptyList())
    val news : List<Article>get() = _news.value
    fun searchNews(query: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitServiceFactory.api.getNews(
                    query,
                    apiKey
                )
                _news . value = response . articles
            } catch (e: Exception) {
                Log.d("ApiViewModel", "Error al obtener las noticias: ${e.message}")
            }
        }
    }
}

