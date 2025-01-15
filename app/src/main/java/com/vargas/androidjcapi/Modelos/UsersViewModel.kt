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

class UsersViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    fun iniciarSession(email: String, password: String, context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                actualizarDatosAcceso(userId, context, onSuccess, onFailure)
            } else {
                _showDialog.value = true
                onFailure()
            }
        }
    }

    fun registrarDatos(nombre: String, email: String, password: String, context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                val user = Usuario(nombre, email, 1,FechaTiempoActual())
                GuardarEnFirestore(userId, user, context, onSuccess, onFailure)
            } else {
                onFailure()
            }
        }
    }

    private fun actualizarDatosAcceso(userId: String, context: Context, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val userRef = firestore.collection("usuarios").document(userId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val contadorActual = snapshot.getLong("contadorAcceso") ?: 0
            val nuevoContador = contadorActual + 1
            val nuevaFecha = FechaTiempoActual()

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

    private fun FechaTiempoActual(): String {
        val tiempo = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return tiempo.format(Date())
    }

    fun DialogoDismiss() {
        _showDialog.value = false
    }
}
