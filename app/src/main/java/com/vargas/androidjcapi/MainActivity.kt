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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vargas.androidjcapi.ui.theme.AndroidJCApiTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var firestore: FirebaseFirestore
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
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
                            RegistroScreen(modifier = Modifier,navController)
                        }
                        composable("home") {
                            HomeScreen(modifier = Modifier,navController)
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
        BotonIniciarSesionConDialogo(navController,email,password)
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
fun BotonIniciarSesionConDialogo(navController: NavController, email: String, password: String) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    val context = LocalContext.current

    ElevatedButton(
        onClick = {
            if (email.isNotBlank() && password.isNotBlank()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            actualizarDatosUsuario(userId, context)
                            navController.navigate("home") // Navega a la pantalla principal
                        }
                    } else {
                        mostrarDialogo = true // Muestra el diálogo si la autenticación falla
                    }
                }
            } else {
                Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier.padding(top = 16.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFF6200EE)
        )
    ) {
        Text(
            text = "Iniciar Sesión",
            color = Color.White
        )
    }

    // Muestra el diálogo en caso de error o registro necesario
    if (mostrarDialogo) {
        DialogoRegistro(
            onConfirm = {
                mostrarDialogo = false
                navController.navigate("registro") // Navega a registro
            },
            onDismiss = { mostrarDialogo = false }
        )
    }
}

fun actualizarDatosUsuario(userId: String, context: Context) {
    val userRef = firestore.collection("usuarios").document(userId)

    firestore.runTransaction { transaction ->
        val snapshot = transaction.get(userRef)
        val contadorActual = snapshot.getLong("contadorAcceso") ?: 0
        val nuevoContador = contadorActual + 1
        val nuevaFecha = CurrentDateTime()

        transaction.update(userRef, "contadorAcceso", nuevoContador)
        transaction.update(userRef, "fechaIngreso", nuevaFecha)
    }.addOnSuccessListener {
        Toast.makeText(context, "Datos de acceso actualizados", Toast.LENGTH_SHORT).show()
    }.addOnFailureListener { e ->
        Toast.makeText(context, "Error al actualizar datos: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

// Función para obtener la fecha y hora actuales
fun CurrentDateTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}

@Composable
fun DialogoRegistro(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Registro necesario") },
        text = { Text(text = "¿Deseas registrarte?") },
        confirmButton = {
            TextButton(onClick = onConfirm) { // Navegar a registro
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AndroidJCApiTheme {
        LoginScreen(modifier = Modifier.fillMaxSize(), navController = rememberNavController())
    }
}