package com.vargas.androidjcapi

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vargas.androidjcapi.Modelos.UsersViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@Composable
fun RegistroScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: UsersViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

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
            onClick = {
                if (validacion(nombre, email, password, repeatPassword)) {
                    viewModel.registrarDatos(nombre, email, password, context,
                        onSuccess = { navController.navigate("login") },
                        onFailure = { Toast.makeText(context, "Error en el registro", Toast.LENGTH_SHORT).show() })
                } else {
                    Toast.makeText(context, "Completa los campos correctamente", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar", color = Color.White)
        }
    }
}

fun validacion(nombre: String, email: String, password: String, repeatPassword: String): Boolean {
    return nombre.isNotBlank() && email.isNotBlank() &&
            password.isNotBlank() && repeatPassword.isNotBlank() &&
            password == repeatPassword
}


@Composable
fun NombreField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Nombre") },
        modifier = Modifier.fillMaxWidth()
    )
}

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

@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Contraseña") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation()
    )
}

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
//El que vale
@Preview(showBackground = false, showSystemUi = false)
@Composable
fun RegistroScreenPreview() {
    AndroidJCApiTheme {
        RegistroScreen(modifier = Modifier.fillMaxSize(), navController = NavController(LocalContext.current))
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