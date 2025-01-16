package com.vargas.androidjcapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vargas.androidjcapi.Modelos.ApiViewModel
import com.vargas.androidjcapi.ui.NewsSearchScreen
import com.vargas.androidjcapi.ui.theme.AndroidJCApiTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidJCApiTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                    ) {
                        composable("login") {
                            LoginScreen(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                navController = navController
                            )
                        }
                        composable("registro") {
                            RegistroScreen(modifier = Modifier, navController = navController)
                        }
                        composable("home") {
                             val viewModel: ApiViewModel by viewModels()
                            val apikey="4db951246eef4511bd2938354eee7887"
                            NewsSearchScreen(viewModel, apikey)
                        }
                    }
                }
            }
        }
    }
}
