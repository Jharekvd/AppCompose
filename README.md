# Proyecto de Consulta de Noticias

## Descripción
Aplicación que utiliza la API de noticias [NewsAPI](https://newsapi.org/) para obtener y mostrar resultados en tiempo real. Permite a los usuarios autenticarse, gestionar sus preferencias y recibir notificaciones similares a plataformas como YouTube o Gmail.

## Pasos a seguir para usar la aplicación
1. Iniciar la aplicación.
2. Registrarse en la aplicación.
3. Iniciar sesión con el correo y contraseña.
4. Realizar una consulta en la pantalla de búsqueda.
5. Salir de la aplicación.

## Funcionalidades Principales
- **Consulta de Noticias**: Obtención de noticias en tiempo real desde la API de NewsAPI.
- **Autenticación con Firebase**: Inicio de sesión y registro de usuarios utilizando Firebase Authentication.
- **Gestión de Datos en Firestore**: Registro y almacenamiento de información del usuario en Firebase Firestore.
- **Notificaciones**: Sistema de notificaciones personalizadas que alerta al usuario para realizar una consulta al ingresar a la aplicación.
- **Carga de Imágenes con Coil**: Uso de la librería Coil para mostrar imágenes de noticias de manera eficiente.
- **Uso de Retrofit**: Comunicación con la API mediante Retrofit para realizar solicitudes HTTP.
- **Multitarea con Hilos**: Implementación de hilos para gestionar procesos en segundo plano, como la descarga de noticias y la carga de imágenes, mejorando así el rendimiento de la app.

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
   
## Commits Importantes
1. **Implementación de Firebase** - [Ver commit](https://github.com/Jharekvd/AppCompose/commit/69d804906f16038a3fb3988b7a4c2e5ef4ee29b7)
2. **Añadir registro en Firebase** - [Ver commit](https://github.com/Jharekvd/AppCompose/commit/3c3d6f3001f6495bffab5271599f5f74040dda24#diff-7c14638f1d1c7e8258381c3d0d9522cf931c2fcb4658dc23ff701f883201fd80)
3. **Implementación de  Nav Controller** - [ver commit](https://github.com/Jharekvd/AppCompose/commit/159557cb3df82725b6f3df1308df55152edddd49)
4. **Organizacion de Codigo MVVM Firebase** - [ver commit](https://github.com/Jharekvd/AppCompose/commit/d0497d449c64b2fdd6df7b2981baa236d04428a4)
5. **Api Noticias** - [ver commit](https://github.com/Jharekvd/AppCompose/commit/99bcb3c55c498c1686e45f726f8b8a488a1fd465)
6. **Implementacion de la notificacion** - [ver commit](https://github.com/Jharekvd/AppCompose/commit/0d056004b49d79426f2b380652157ed88fe438db)
7. **Organizacion de Codigo** - [ver commit](https://github.com/Jharekvd/AppCompose/commit/e50c61c171aaeaf3b9f063bf22c8f93b16ec1fd1)
8. **Utimos ajustes para la decoracion de las pantallas de registrar, login y consultas** - [ver commit](https://github.com/Jharekvd/AppCompose/commit/eb300ac588e3d24aa9ce677de693622ca57a6115)

## Enlaces Útiles
- [NewsAPI](https://newsapi.org/)
- [Firebase](https://firebase.google.com/)
- [Retrofit](https://square.github.io/retrofit/)
- [Coil](https://coil-kt.github.io/coil/)
