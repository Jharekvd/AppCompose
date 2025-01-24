package com.vargas.androidjcapi.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vargas.androidjcapi.ui.theme.AndroidJCApiTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.vargas.androidjcapi.Modelos.UsersViewModel


// Definición de la pantalla de registro
@Composable
fun RegistroScreen(
    modifier: Modifier = Modifier,  // Parámetro para el modificador, que permite personalizar la apariencia
    navController: NavController,   // Controlador de navegación para la transición entre pantallas
    viewModel: UsersViewModel = viewModel()  // ViewModel que maneja la lógica del regis
) {
    // Declaración de los estados de las variables que se usan para manejar los valores de los campos de texto
    var nombre by remember { mutableStateOf("") }  // Nombre del usuario
    var email by remember { mutableStateOf("") }   // Email del usuario
    var password by remember { mutableStateOf("") }  // Contraseña
    var repeatPassword by remember { mutableStateOf("") }  // Repetir contraseña
    val context = LocalContext.current  // Obtiene el contexto actual de la aplicación

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,// Centra el contenido verticalmente
        horizontalAlignment = Alignment.CenterHorizontally// Centra el contenido horizontalmente
    ) {
        //Este it es para que el usuario permitiendole cambiar el valor
        NombreField(nombre) { nombre = it }
        Spacer(modifier = Modifier.height(8.dp))
        EmailField(email) { email = it }
        Spacer(modifier = Modifier.height(8.dp))
        PasswordField(password) { password = it }
        Spacer(modifier = Modifier.height(8.dp))
        RepeatPasswordField(repeatPassword) { repeatPassword = it }
        Spacer(modifier = Modifier.height(16.dp))
        // Botón para realizar el registro
        ElevatedButton(
            colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF6200EE)),
            onClick = {
                // Validación de los campos antes de proceder
                if (validacion(nombre, email, password, repeatPassword)) {
                    // Si la validación es exitosa, se llama al ViewModel para registrar al usuario
                    viewModel.registrarDatos(nombre, email, password, context,
                        onSuccess = { navController.navigate("login") }, // Redirige a la pantalla de login si el registro es exitoso
                        onFailure = {
                            Toast.makeText(
                                context,
                                "Error en el registro",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) // Muestra un mensaje si falla
                } else {
                    // Si la validación falla, muestra un mensaje de error
                    Toast.makeText(context, "Completa los campos correctamente", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            modifier = Modifier.fillMaxWidth() // Hace que el botón ocupe todo el ancho disponible
        ) {
            Text(text = "Registrar", color = Color.White)// Texto dentro del botón
        }
    }
}


fun validacion(nombre: String, email: String, password: String, repeatPassword: String): Boolean {
    return nombre.isNotBlank() && email.isNotBlank() && // Verifica que los campos no estén vacíos
            password.isNotBlank() && repeatPassword.isNotBlank() &&// Verifica que las contraseñas no estén vacías
            password == repeatPassword // Verifica que las contraseñas coincidan
}

//Composable para el campo de nombre
@Composable
fun NombreField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,  // El valor actual del campo
        onValueChange = onValueChange,  // Acción que se ejecuta al cambiar el valor
        label = { Text("Nombre") },  // Etiqueta del campo
        modifier = Modifier.fillMaxWidth()  // Hace que el campo ocupe todo el ancho disponible
    )
}

//Composable para el cammpo
@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}
// Composable para mostrar el campo de contraseña
@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,  // El valor actual del campo
        onValueChange = onValueChange,  // Acción que se ejecuta al cambiar el valor
        label = { Text("Contraseña") },  // Etiqueta del campo
        modifier = Modifier.fillMaxWidth(),  // Hace que el campo ocupe todo el ancho disponible
        visualTransformation = PasswordVisualTransformation()  // Transforma el texto para ocultar la contraseña
    )
}
// Composable para mostrar el campo para repetir la contraseña
@Composable
fun RepeatPasswordField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Repetir Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation()
    )
}
// Vista previa de la pantalla de registro para ver cómo se verá en la interfaz
//El que vale
@Preview(showBackground = false, showSystemUi = false)
@Composable
fun RegistroScreenPreview() {
    AndroidJCApiTheme {
        RegistroScreen(
            modifier = Modifier.fillMaxSize(),
            navController = NavController(LocalContext.current)
        )
    }
}

//EL de prueba
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroScreenPreview1() {
    AndroidJCApiTheme {
        RegistroScreenPreviewFriendly()
    }
}

@Composable
fun RegistroScreenPreviewFriendly(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NombreField(nombre) { nombre = it }
        Spacer(modifier = Modifier.height(8.dp))
        EmailField(email) { email = it }
        Spacer(modifier = Modifier.height(8.dp))
        PasswordField(password) { password = it }
        Spacer(modifier = Modifier.height(8.dp))
        RepeatPasswordField(repeatPassword) { repeatPassword = it }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(
            colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF6200EE)),
            onClick = { /* No hacer nada en el preview */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar", color = Color.White)
        }
    }
}