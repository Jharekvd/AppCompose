# Proyecto de Consulta de Noticias

## Descripción
Aplicación que utiliza una API de noticias [News Api](https://newsapi.org/) para obtener y mostrar resultados en tiempo real. Permite a los usuarios autenticarse, gestionar sus preferencias y recibir notificaciones similares a plataformas como YouTube o Gmail.

## Funcionalidades Principales
- **Consulta de Noticias**: Obtención de noticias en el momento desde la API de NewsAPI.
- **Autenticación con Firebase**: Inicio de sesión y registro de usuarios utilizando Firebase Authentication.
- **Gestión de Datos en Firestore**: Registro y almacenamiento de información del usuario en Firebase Firestore.
- **Notificaciones**: Sistema de notificaciones personalizadas para alertar que hagas una consulta cuando entre a la aplicacion.
- **Carga de Imágenes con Coil**: Uso de la librería Coil para mostrar imágenes de noticias eficientemente.
- **Uso de Retrofit**: Comunicación con la API mediante Retrofit para realizar solicitudes HTTP.
- **Multitarea con Hilos**: Implementación de hilos para gestionar procesos en segundo plano y mejorar el rendimiento de la app.

## Tecnologías Utilizadas
- **Firebase Authentication**: Para la autenticación de usuarios.
- **Firebase Firestore**: Para el almacenamiento de datos en la nube.
- **Retrofit**: Para manejar las solicitudes HTTP hacia la API de noticias.
- **Coil**: Para la carga de imágenes.
- **API NewsAPI**: Fuente de datos de noticias en tiempo real.
- **Hilos**: Para operaciones en segundo plano.

## Objetivos
1. Proveer una experiencia de usuario fluida y personalizada.
2. Permitir a los usuarios estar informados con noticias relevantes y actualizadas.
3. Garantizar la seguridad y la gestión eficiente de datos con Firebase.
## Enlaces Útiles
- [NewsAPI](https://newsapi.org/)
- [Firebase](https://firebase.google.com/)
- [Retrofit](https://square.github.io/retrofit/)
- [Coil](https://coil-kt.github.io/coil/)
