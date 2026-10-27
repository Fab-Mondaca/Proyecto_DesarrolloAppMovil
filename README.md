Martini Food Labs – Plataforma móvil de contenidos técnicos

Aplicación móvil Android (MVP académico) desarrollada para la asignatura DSY1105 – Desarrollo de Aplicaciones Móviles (Duoc UC), Evaluación Parcial 2.

Contexto del caso

Master Martini Chile SPA es una comercializadora de insumos para alimentos que quiere acercarse a clientes y prospectos (panaderías, pastelerías, chocolaterías, casinos, restaurantes) mediante recetas, videos técnicos y cursos. Hoy los videos se envían por WhatsApp y YouTube, sin una plataforma dedicada.

Esta app es un prototipo que permite:

Registrar perfiles de clientes con su tipo de negocio (información segmentada para futuras acciones de contacto).
Consultar recetas técnicas por categoría.
Guardar recetas favoritas.
Usar la app con una interfaz simple y accesible para personas con menor manejo digital o dificultades visuales (modo de texto grande).

Todos los datos y contenidos son ficticios o simulados. No se usan datos personales reales ni material interno de la organización.

Integrante - Nombre	GitHub
Fabián Mondaca - @Fab-Mondaca

Equipo: Grupo 3 · Asignatura: DSY1105 · Sede Duoc Antonio Varas


Funcionalidades implementadas

Interfaz y navegación

Navegación entre pantallas con Navigation Compose (login, registro, resumen, inicio, recetas, detalle, guardadas, perfil, ajustes).
Diseño con identidad visual propia (tema, colores y tipografía), botones grandes y diseño adaptable a distintos tamaños de pantalla.

Formularios validados

Registro (nombre, correo, teléfono, RUT de empresa, tipo de negocio, contraseña, confirmación y términos), inicio de sesión y edición de perfil.
Cada campo muestra ícono, borde de error y mensaje explicativo en tiempo real.
Validación del RUT chileno con dígito verificador, formato de correo y teléfono chileno.

Lógica desacoplada (MVVM)

Las reglas de validación están centralizadas en Validadores.kt.
Los ViewModel exponen el estado con StateFlow; las pantallas solo lo muestran.

Persistencia local

Room (SQLite): usuarios y recetas guardadas.
DataStore: preferencia de accesibilidad (texto grande).
Operaciones de crear, leer, actualizar y eliminar cuenta.

Animaciones

Transiciones entre pantallas, aparición animada de errores y mensajes, cambio de color animado en botones y Crossfade en imágenes.

Recursos nativos del dispositivo

Cámara (TakePicture + FileProvider) con solicitud de permiso en tiempo de ejecución.
Galería de imágenes para la foto de perfil.
Ubicación aproximada (ACCESS_COARSE_LOCATION) con permiso en tiempo de ejecución.
Los permisos se prueban sin capturar datos reales.
