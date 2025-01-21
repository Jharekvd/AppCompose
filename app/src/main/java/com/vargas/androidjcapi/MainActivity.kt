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
    private val handler = Handler(Looper.getMainLooper())
    private var isWorkerRunning = false
    private lateinit var notificacionManager: NotificationManager
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "notificacion_id"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        notificacionManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        setContent {
            AndroidJCApiTheme {
                Navegacion()
                startTimeWorker()
            }
        }
    }
    private fun createNotificationChannel() {
        val canal = NotificationChannel(CHANNEL_ID,"Canal de notificacion", NotificationManager.IMPORTANCE_HIGH).apply{
            description= "Esta es una notificaion para recordarte que puedes hacer consultas"
        }
        notificacionManager.createNotificationChannel(canal)
    }
    // Iniciar el worker de la hora, independiente de las pantallas
    fun startTimeWorker() {
        if (!isWorkerRunning) {
            isWorkerRunning = true

            handler.post(object : Runnable {
                override fun run() {
                    // Actualizar la hora
                    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                    showNotificacion("Son las $currentTime, debes hacer una consulta en la api")

                    // Repetir cada cada minuto
                  handler.postDelayed(this, 60*100) // 60 segundos en milisegundos
                }
            })
        }
    }
    private fun showNotificacion(message: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Recordatorio")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificacionManager.notify(NOTIFICATION_ID, notification)
    }

    // Función para detener el worker
    fun stopTimeWorker() {
        if (isWorkerRunning) {
            isWorkerRunning = false
            handler.removeCallbacksAndMessages(null) // Detener todas las tareas pendientes
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        stopTimeWorker()
    }
}