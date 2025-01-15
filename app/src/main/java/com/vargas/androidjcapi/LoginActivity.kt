package com.vargas.androidjcapi

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vargas.androidjcapi.Modelos.UsersViewModel
import com.vargas.androidjcapi.ui.theme.AndroidJCApiTheme


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: UsersViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EntradaEmail(email) { email = it }
        EntradaPassWord(password) { password = it }
        BotonIniciarSesionConDialogo(navController, email, password, viewModel)
    }
}

@Composable
fun BotonIniciarSesionConDialogo(
    navController: NavController,
    email: String,
    password: String,
    viewModel: UsersViewModel = viewModel()
) {
    val context = LocalContext.current
    val showDialog by viewModel.showDialog.collectAsState()

    ElevatedButton(
        onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                viewModel.iniciarSession(email, password, context,
                    onSuccess = { navController.navigate("home") },
                    onFailure = { /* Manejo de error si es necesario */ })
            } else {
                Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier.padding(top = 16.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF6200EE))
    ) {
        Text(text = "Iniciar Sesión", color = Color.White)
    }

    if (showDialog) {
        DialogoRegistro(
            onConfirm = {
                viewModel.DialogoDismiss()
                navController.navigate("registro")
            },
            onDismiss = {
                viewModel.DialogoDismiss()
            }
        )
    }
}

@Composable
fun EntradaEmail(email: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        label = { Text("Nombre") }
    )
}

@Composable
fun EntradaPassWord(password: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun DialogoRegistro(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Registro necesario") },
        text = { Text(text = "¿Deseas registrarte?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Sí")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "No")
            }
        }
    )
}
//No prueba
@Preview(showBackground = false, showSystemUi =false)
@Composable
fun LoginScreenPreview() {
    AndroidJCApiTheme {
        LoginScreen(modifier = Modifier.fillMaxSize(), navController = rememberNavController())
    }
}

//Prueba
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreviewPrueba() {
    AndroidJCApiTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Hola") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF6200EE),
                        titleContentColor = Color.White
                    )
                )
            },
            content = { innerPadding ->
                LoginScreenPreviewFriendly(modifier = Modifier.padding(innerPadding))
            },
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { /* Acción para seleccionar Home */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = { /* Acción para seleccionar Perfil */ }
                )
            }



        )
    }
}
@Composable
fun LoginScreenPreviewFriendly(
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EntradaEmail(email) { email = it }
        EntradaPassWord(password) { password = it }
        ElevatedButton(
            onClick = { /* No hacer nada en el preview */ },
            modifier = Modifier.padding(top = 16.dp),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "Iniciar Sesión", color = Color.White)
        }
    }
}
