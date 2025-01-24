package com.vargas.androidjcapi

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vargas.androidjcapi.ui.theme.AndroidJCApiTheme
import android.os.Looper
import android.os.Handler
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    // Handler para ejecutar tareas en el hilo principal.
    private val handler = Handler(Looper.getMainLooper())

    // Bandera para verificar si el worker está corriendo.
    private var isWorkerRunning = false

    // Notificación manager para mostrar notificaciones.
    private lateinit var notificacionManager: NotificationManager

    // Constantes para el ID de la notificación y el canal.
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "notificacion_id"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilitar el diseño edge-to-edge para una experiencia inmersiva.
        enableEdgeToEdge()
        // Inicializar el NotificationManager.
        notificacionManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Crear el canal de notificación necesario para Android 8+.
        createNotificationChannel()
        setContent {
            AndroidJCApiTheme {
                // Funcion de navegacion de la aplicación.
                Navegacion()
                // Iniciar el worker que muestra las notificaciones cada minuto.
                startTimeWorker()
            }
        }
    }

    // Crear un canal de notificación para notificaciones importantes.
    private fun createNotificationChannel() {
        val canal = NotificationChannel(
            CHANNEL_ID,
            "Canal de notificacion",
            NotificationManager.IMPORTANCE_HIGH // Importancia alta para notificaciones visibles.
        ).apply {
            description = "Esta es una notificaion para recordarte que puedes hacer consultas"
        }
        // Registrar el canal en el NotificationManager.
        notificacionManager.createNotificationChannel(canal)
    }

    // Iniciar el worker de la hora, independiente de las pantallas
    fun startTimeWorker() {
        if (!isWorkerRunning) {
            isWorkerRunning = true
            // Ejecutar una tarea periódica utilizando el Handler.
            handler.post(object : Runnable {
                override fun run() {
                    // Obtener la hora actual en formato HH:mm.
                    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                    // Mostrar una notificación con un mensaje personalizado.
                    showNotificacion("Son las $currentTime, debes hacer una consulta en la api")

                    // Repetir esta tarea después de 1 minuto (60,000 ms).
                    handler.postDelayed(this, 60 * 1000)
                }
            })
        }
    }
    // Mostrar una notificación con un mensaje específico.
    private fun showNotificacion(message: String) {
        // Crear un Intent para abrir la MainActivity al hacer clic en la notificación.
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // Crear un PendingIntent para retrasar la ejecución del Intent hasta que el usuario interactúe con la notificación.
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        // Construir la notificación.
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Recordatorio") // Título de la notificación.
            .setContentText(message) // Mensaje principal de la notificación.
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Icono que se muestra en la notificación.
            .setContentIntent(pendingIntent) // Acción al hacer clic en la notificación.
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Alta prioridad para visibilidad inmediata.
            .build()
        // Mostrar la notificación.
        notificacionManager.notify(NOTIFICATION_ID, notification)
    }

    // Función para detener el worker
    fun stopTimeWorker() {
        if (isWorkerRunning) {
            isWorkerRunning = false
            handler.removeCallbacksAndMessages(null) // Detener todas las tareas pendientes
        }
    }

    // Es para detener la notificacion de la aplicacion cuando se cierre por completo.
    override fun onDestroy() {
        super.onDestroy()
        stopTimeWorker()// Detener el worker cuando se destruya la actividad.
    }
}