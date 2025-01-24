package com.vargas.androidjcapi.Screens
import com.vargas.androidjcapi.R
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.vargas.androidjcapi.MainActivity
import com.vargas.androidjcapi.Modelos.ApiViewModel

// Pantalla principal donde se inicializa el ViewModel y se pasa la clave API para buscar noticias
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsSearchScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: ApiViewModel =
        viewModel()  // Se inicializa el ViewModel que gestionará las noticias
    val apiKey = "4db951246eef4511bd2938354eee7887"  // Clave API para autenticar las peticiones
    var query by remember { mutableStateOf("") }  // Estado para guardar el valor de la búsqueda
    val news = viewModel.news  // Se obtiene la lista de noticias del ViewModel
    val context = LocalContext.current  // Se obtiene el contexto actual de la aplicación
    val activity = context as? MainActivity  // Se obtiene la actividad actual (MainActivity)
    // Detener cualquier trabajo que esté en segundo plano, como un temporizador en la MainActivity
    LaunchedEffect(Unit) {
        activity?.stopTimeWorker()
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier= Modifier.fillMaxWidth()){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), // Asegura que ocupe todo el ancho
                    horizontalArrangement = Arrangement.SpaceEvenly // Distribuye los íconos equitativamente
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("login") // Lógica para regresar a la pantalla de login
                        },
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_login_24), // Recurso Login
                            contentDescription = "Login",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    IconButton(
                        onClick = {
                            activity?.finish()  // Finaliza la actividad, cerrando la aplicación
                        },
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_home_filled_24), // Recurso Casa
                            contentDescription = "Salir",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Campo de texto para que el usuario ingrese la consulta de búsqueda
            OutlinedTextField(
                value = query,  // El valor del campo de texto
                onValueChange = { query = it },  // Función para actualizar el valor del campo
                label = { Text("Buscar Noticias") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Botón para ejecutar la búsqueda de noticias
            Button(
                onClick = {
                    viewModel.searchNews(
                        query,
                        apiKey
                    )
                },// Cuando se hace clic, se llama a la función de búsqueda
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Buscar")// Texto que aparece en el botón
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Lista de noticias que se mostrará debajo del campo de búsqueda
            LazyColumn {
                items(news) { article ->// Itera sobre la lista de noticias y muestra cada artículo
                    Column(modifier = Modifier.padding(8.dp)) {// Cada artículo se envuelve en una columna
                        // Si el artículo tiene una imagen, se muestra
                        article.urlToImage?.let {
                            Image(
                                painter = rememberAsyncImagePainter(it),  // Carga la imagen de la URL proporcionada
                                contentDescription = null,  // No se requiere descripción de la imagen
                                contentScale = ContentScale.Crop,  // Ajusta la imagen para que se recorte y ocupe el espacio adecuado
                                modifier = Modifier
                                    .fillMaxWidth()  // La imagen ocupa todo el ancho disponible
                                    .height(200.dp)  // La altura de la imagen se establece en 200dp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = article.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = article.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

