package com.vargas.androidjcapi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.vargas.androidjcapi.Modelos.ApiViewModel


@Composable
fun HomeScreen() {
    val viewModel: ApiViewModel = viewModel() // Inicializar ViewModel
    val apiKey = "4db951246eef4511bd2938354eee7887" // Clave API
    NewsSearchScreen(viewModel, apiKey)
}
@Composable
fun NewsSearchScreen(viewModel: ApiViewModel, apiKey: String) {
    var query by remember { mutableStateOf("") }
    val news = viewModel.news
    val context = LocalContext.current
    val activity = context as? MainActivity
    LaunchedEffect(Unit) {
        activity?.stopTimeWorker()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar Noticias") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.searchNews(query, apiKey) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buscar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(news) { article ->
                Column(modifier = Modifier.padding(8.dp)) {
                    article.urlToImage?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = article.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = article.description, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

