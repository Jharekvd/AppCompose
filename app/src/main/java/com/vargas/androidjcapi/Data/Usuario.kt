package com.vargas.androidjcapi.Data

// Clase Usuario para almacenar información de un usuario en Firebase.
data class Usuario(
    val nombre: String,         // Nombre del usuario.
    val email: String,          // Correo electrónico del usuario.
    val contadorAcceso: Int,    // Número de accesos realizados por el usuario.
    val fechaIngreso: String    // Fecha en que el usuario se registró o ingresó.
)