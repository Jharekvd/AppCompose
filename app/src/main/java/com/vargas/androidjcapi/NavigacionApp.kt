package com.vargas.androidjcapi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vargas.androidjcapi.Screens.LoginScreen
import com.vargas.androidjcapi.Screens.NewsSearchScreen
import com.vargas.androidjcapi.Screens.RegistroScreen


@Composable
fun Navegacion(){
    // Crear un controlador de navegación para gestionar las rutas.
    val navController = rememberNavController()
    // Usar Scaffold para manejar la estructura básica de la UI, como la barra de herramientas y el contenido.
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        // Configurar el NavHost para gestionar las pantallas de la aplicación.
        NavHost(
            navController = navController, // Controlador de navegación
            startDestination = "login", // Pantalla inicial (login)
            modifier = Modifier.padding(innerPadding) // Ajuste de padding para evitar que el contenido quede debajo de elementos UI.
        ) {
            // Definimos las rutas de las pantallas disponibles en la navegación.
            //Pantalla login
            composable("login") {
                LoginScreen(
                    modifier = Modifier.fillMaxSize(), // Se llena todo el espacio disponible.
                    navController = navController // Pasar el controlador de navegación.
                )
            }
            //Pantalla de Registro
            composable("registro") {
                RegistroScreen(
                    modifier = Modifier.fillMaxSize(), // Se llena todo el espacio disponible.
                    navController = navController // Pasar el controlador de navegación.
                )
            }
            // Pantalla de Home
            composable("home") {
                NewsSearchScreen(
                    modifier = Modifier.fillMaxSize(), // Se llena todo el espacio disponible.
                    navController = navController // Pasar el controlador de navegación.
                )
            }
        }
    }
}
