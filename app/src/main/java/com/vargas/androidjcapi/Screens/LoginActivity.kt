package com.vargas.androidjcapi.Screens

import android.content.Context
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
import com.vargas.androidjcapi.MainActivity
import com.vargas.androidjcapi.Modelos.UsersViewModel
import com.vargas.androidjcapi.ui.theme.AndroidJCApiTheme


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: UsersViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }  // Estado para almacenar el email
    var password by rememberSaveable { mutableStateOf("") }  // Estado para almacenar la contraseña
    val context = LocalContext.current  // Contexto de la aplicación
    val activity = context as? MainActivity  // Obtiene la actividad principal para interactuar con ella

    // Iniciar el worker cuando se entra en la pantalla principal
    LaunchedEffect(Unit) {
        activity?.startTimeWorker()// Inicia el worker para mostrar notificaciones
    }
    // Diseño de la pantalla de login
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,  // Centra los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally  // Centra los elementos horizontalmente
    ) {
        EntradaEmail(email) { email = it }  // Componente de entrada para el email
        EntradaPassWord(password) { password = it }  // Componente de entrada para la contraseña
        BotonIniciarSesionConDialogo(navController, email, password, viewModel)  // Botón de inicio de sesión
    }
}
// Función que muestra un mensaje de error
fun mensaje(context: Context) {
        Toast.makeText(context, "No estas registrado o la contraseña es incorrecta", Toast.LENGTH_SHORT).show();
}
// Componente del botón de inicio de sesión con control de errores
@Composable
fun BotonIniciarSesionConDialogo(
    navController: NavController,
    email: String,
    password: String,
    viewModel: UsersViewModel = viewModel()
) {
    val context = LocalContext.current
    val showDialog by viewModel.showDialog.collectAsState()// Observa si el diálogo debe mostrarse

    ElevatedButton(
        onClick = {
            // Si el email y la contraseña no están vacíos
            if (email.isNotBlank() && password.isNotBlank()) {
                // Intenta iniciar sesión
                viewModel.iniciarSession(email, password, context,
                    onSuccess = { navController.navigate("home") },  // Si el inicio de sesión es exitoso, navega a home
                    onFailure = { mensaje(context) })  // Si hay error, muestra un mensaje
            }else{
                Toast.makeText(context, "Por favor rellena todos los campos", Toast.LENGTH_SHORT).show();
            }
        },
        modifier = Modifier.padding(top = 16.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF6200EE))
    ) {
        Text(text = "Iniciar Sesión", color = Color.White)// Texto del botón
    }
    // Si el diálogo debe mostrarse, muestra el diálogo de registro
    if (showDialog) {
        DialogoRegistro(
            onConfirm = {
                viewModel.DialogoDismiss()  // Cierra el diálogo
                navController.navigate("registro")  // Navega a la pantalla de registro
            },
            onDismiss = {
                viewModel.DialogoDismiss()// Cierra el diálogo
            }
        )
    }
}
// Componente para la entrada de email
@Composable
fun EntradaEmail(email: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onValueChange,
        label = { Text("Nombre") }
    )
}
// Componente para la entrada de contraseña
@Composable
fun EntradaPassWord(password: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onValueChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(), // Enmascara la contraseña
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)  // Configura el tipo de teclado para contraseñas
    )
}
// Diálogo para preguntar si el usuario desea registrarse
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
// Vista previa del componente LoginScreen para la interfaz
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
            content = { innerPadding ->
                LoginScreenPreviewFriendly(modifier = Modifier.padding(innerPadding))
            }
        )
    }
}
//Prueba
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
