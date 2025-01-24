package com.vargas.androidjcapi.Modelos

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vargas.androidjcapi.Data.Usuario
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
// ViewModel para gestionar la autenticación de usuarios y el almacenamiento de datos en Firebase.
class UsersViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance() // Instancia de FirebaseAuth para manejar autenticación.
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance() // Instancia de FirebaseFirestore para manejar datos.

    private val _showDialog = MutableStateFlow(false) // Estado para controlar si se muestra un diálogo de error.
    val showDialog: StateFlow<Boolean> = _showDialog // Exposición del estado de diálogo como flujo inmutable.

    // Función para iniciar sesión con correo y contraseña.
    fun iniciarSession(email: String, password: String, context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Obtiene el ID del usuario autenticado.
                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                ActualizarDatosAcceso(userId, context, onSuccess, onFailure) // Actualiza datos del usuario en Firestore.
            } else {
                _showDialog.value = true // Muestra el diálogo en caso de error.
                onFailure()
            }
        }
    }
    // Función para registrar un nuevo usuario y guardar sus datos en Firestore.
    fun registrarDatos(nombre: String, email: String, password: String, context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Obtiene el ID del usuario recién registrado.
                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                val user = Usuario(nombre, email, 1, FechaTiempoActual()) // Crea un objeto Usuario con los datos iniciales.
                //Funcion
                GuardarEnFirestore(userId, user, context, onSuccess, onFailure) // Guarda el usuario en Firestore.
            } else {
                onFailure()
            }
        }
    }
    // Función para actualizar los datos de acceso del usuario en Firestore.
    private fun ActualizarDatosAcceso(userId: String, context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val userRef = firestore.collection("usuarios").document(userId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val contadorActual = snapshot.getLong("contadorAcceso") ?: 0 // Obtiene el contador actual.
            val nuevoContador = contadorActual + 1 // Incrementa el contador.
            val nuevaFecha = FechaTiempoActual() // Obtiene la fecha actual.

            // Actualiza los datos en Firestore.
            transaction.update(userRef, "contadorAcceso", nuevoContador)
            transaction.update(userRef, "fechaIngreso", nuevaFecha)
        }.addOnSuccessListener {
            Toast.makeText(context, "Datos de acceso actualizados", Toast.LENGTH_SHORT).show()
            onSuccess()
        }.addOnFailureListener { e ->
            Toast.makeText(context, "Error al actualizar datos: ${e.message}", Toast.LENGTH_SHORT).show()
            onFailure()
        }
    }
    // Función para guardar los datos del usuario en Firestore al registrarlo.
    private fun GuardarEnFirestore(userId: String, user: Usuario, context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        firestore.collection("usuarios").document(userId)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                onSuccess()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al guardar en Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                onFailure()
            }
    }
    // Función para obtener la fecha y hora actual en el formato "yyyy-MM-dd HH:mm:ss".
    private fun FechaTiempoActual(): String {
        val tiempo = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return tiempo.format(Date())
    }
    // Función para cerrar el diálogo de error.
    fun DialogoDismiss() {
        _showDialog.value = false
    }
}
