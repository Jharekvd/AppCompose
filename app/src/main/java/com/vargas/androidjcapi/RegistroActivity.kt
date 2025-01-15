package com.vargas.androidjcapi

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private lateinit var auth: FirebaseAuth
private lateinit var firestore: FirebaseFirestore

class RegistroActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance() // Inicializa Firestore
        setContent {
            AndroidJCApiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistroScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun RegistroScreen(modifier: Modifier = Modifier) {
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
        RegistroButton(nombre, email, password, repeatPassword)
    }
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

@Composable
fun RegistroButton(nombre: String, email: String, password: String, repeatPassword: String) {
    val context = LocalContext.current
    ElevatedButton(
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFF6200EE)
        ),
        onClick = { handleRegistro(context, nombre, email, password, repeatPassword) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Registrar", color = Color.White)
    }
}

fun handleRegistro(context: Context, nombre: String, email: String, password: String, repeatPassword: String) { if (nombre.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) { if (password == repeatPassword) { auth.createUserWithEmailAndPassword(email, password) .addOnCompleteListener { task -> if (task.isSuccessful) { val userId = auth.currentUser?.uid val accessTime = FieldValue.serverTimestamp() val userData = hashMapOf( "nombre" to nombre, "email" to email, "contadorAcceso" to 1, "fechaAcceso" to accessTime ) if (userId != null) { firestore.collection("users").document(userId) .set(userData) .addOnSuccessListener { Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show() } .addOnFailureListener { Toast.makeText(context, "Error al guardar los datos", Toast.LENGTH_SHORT).show() } } } else { Toast.makeText(context, "Error en el registro", Toast.LENGTH_SHORT).show() } } } else { Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show() } } else { Toast.makeText(context, "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show() } }
@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    AndroidJCApiTheme {
        RegistroScreen()
    }
}