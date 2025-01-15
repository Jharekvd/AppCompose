package com.vargas.androidjcapi

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.vargas.androidjcapi.ui.theme.AndroidJCApiTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController){

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AndroidJCApiTheme {
        RegistroScreen( modifier = Modifier.fillMaxSize(),navController = NavController(LocalContext.current))
    }
}