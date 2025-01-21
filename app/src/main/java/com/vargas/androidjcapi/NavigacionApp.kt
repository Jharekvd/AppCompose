package com.vargas.androidjcapi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navegacion(){
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
            composable("registro") {
                RegistroScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
            composable("home") {
                HomeScreen()
            }
        }
    }
}
