package com.vargas.androidjcapi.Modelos

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vargas.androidjcapi.Data.Article
import com.vargas.androidjcapi.network.RetrofitServiceFactory
import kotlinx.coroutines.launch

// ViewModel para gestionar la lógica de la API y proporcionar los datos a la UI.
class ApiViewModel : ViewModel() {

    // Estado mutable que almacena la lista de artículos obtenidos de la API.
    private val _news = mutableStateOf<List<Article>>(emptyList())
    val news: List<Article> get() = _news.value // Getter para acceder a la lista de noticias.

    // Función que realiza la búsqueda de noticias utilizando la API.
    fun searchNews(query: String, apiKey: String) {
        // viewModelScope se utiliza para lanzar corrutinas que se cancelan automáticamente cuando el ViewModel se destruye.
        viewModelScope.launch {
            try {
                // Llama al servicio Retrofit para obtener las noticias de acuerdo al query y API key.
                val response = RetrofitServiceFactory.api.getNews(
                    query,
                    apiKey
                )
                // Actualiza la lista de artículos con los datos obtenidos de la respuesta.
                _news.value = response.articles
            } catch (e: Exception) {
                // Registra un error en el log en caso de que falle la solicitud a la API.
                Log.d("ApiViewModel", "Error al obtener las noticias: ${e.message}")
            }
        }
    }
}

