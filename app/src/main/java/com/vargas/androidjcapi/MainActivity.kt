package com.vargas.androidjcapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.vargas.androidjcapi.ui.theme.AndroidJCApiTheme
private lateinit var auth: FirebaseAuth
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        enableEdgeToEdge()
        setContent {
            AndroidJCApiTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController=navController,
                        startDestination = "login",
                    ) {
                        composable("login") {
                            LoginScreen(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),//Ocupa toda la pantalla
                                navController=navController
                            )
                        }
                        composable("registro") {
                            RegistroScreen(navController=navController)
                        }
                        composable("home") {
                            Home(navController=navController)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun LoginScreen( modifier: Modifier = Modifier, navController: NavController) {
    var password by rememberSaveable { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Opcional: margen alrededor
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        EntradaEmail(email){email=it}
        EntradaPassWord(password){password=it}
        BotonIniciarSesionConDialogo()
    }
}

@Composable
fun EntradaEmail(email: String, onValueChange: (String) -> Unit){

    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        label = { Text("Nombre") }
    )

}
@Composable
fun EntradaPassWord(password: String, onValueChange: (String) -> Unit){
    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun BotonIniciarSesionConDialogo() {
    var mostrarDialogo by remember { mutableStateOf(false) }
    // Funcion que llama al boton
    BotonIniciarSesion { mostrarDialogo = true }
    // Diálogo emergente
    if (mostrarDialogo) {
        DialogoRegistro(
            onConfirm = { /* Acción al aceptar (por ejemplo, navegar a registro) */      mostrarDialogo = false },
            onDismiss = { mostrarDialogo = false }
        )
    }
}

@Composable
fun BotonIniciarSesion(onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier.padding(top = 16.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFF6200EE) // Color morado
        )
    ) {
        Text(
            text = "Iniciar Sesión",
            color=Color.White
        )
    }
}
@Composable
fun DialogoRegistro(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Registro necesario") },
        text = { Text(text = "¿Deseas registrarte?") },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(text = "Sí")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "No")
            }
        }
    )
}
@Composable
fun RegistroScreen(navController: NavController){

}
@Composable
fun Home(navController: NavController){

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AndroidJCApiTheme {
        LoginScreen(modifier = Modifier.fillMaxSize(), navController = rememberNavController())
    }
}