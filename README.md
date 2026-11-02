# Martini Food Labs – Plataforma móvil de contenidos técnicos

Aplicación móvil Android (MVP académico) desarrollada para la asignatura **DSY1105 – Desarrollo de Aplicaciones Móviles** (Duoc UC), Evaluación Parcial 2.

## Contexto del caso

**Master Martini Chile SPA** es una comercializadora de insumos para alimentos que quiere acercarse a clientes y prospectos (panaderías, pastelerías, chocolaterías, casinos, restaurantes) mediante recetas, videos técnicos y cursos. Hoy los videos se envían por WhatsApp y YouTube, sin una plataforma dedicada.

Esta app es un prototipo que permite:

- Registrar perfiles de clientes con su tipo de negocio (información segmentada para futuras acciones de contacto).
- Consultar recetas técnicas por categoría.
- Guardar recetas favoritas.
- Acceder a un video técnico de cada receta mediante un enlace a YouTube.
- Usar la app con una interfaz simple y accesible para personas con menor manejo digital o dificultades visuales (modo de texto grande).

> Todos los datos y contenidos son **ficticios o simulados**. No se usan datos personales reales ni material interno de la organización.

## Integrante

| Nombre | GitHub |
|---|---|
| Fabián Mondaca | [@Fab-Mondaca](https://github.com/Fab-Mondaca) |

Equipo: Grupo 3 · Asignatura: DSY1105 · Sede Duoc Antonio Varas

## Funcionalidades implementadas

**Interfaz y navegación**
- Navegación entre pantallas con Navigation Compose (login, registro, resumen, inicio, recetas, detalle, guardadas, perfil, ajustes).
- Diseño con identidad visual propia (tema, colores y tipografía), botones grandes y diseño adaptable a distintos tamaños de pantalla.

**Formularios validados**
- Registro (nombre, correo, teléfono, RUT de empresa, tipo de negocio, contraseña, confirmación y términos), inicio de sesión y edición de perfil.
- Cada campo muestra ícono, borde de error y mensaje explicativo en tiempo real.
- Validación del RUT chileno con dígito verificador, formato de correo y teléfono chileno.

**Lógica desacoplada (MVVM)**
- Las reglas de validación están centralizadas en `Validadores.kt`.
- Los `ViewModel` exponen el estado con `StateFlow`; las pantallas solo lo muestran.

**Contenido y video**
- Catálogo de recetas técnicas por categoría (chocolatería, pastelería, heladería, bollería, masas y empanadas).
- En el detalle de cada receta, la tarjeta "Ver video técnico" abre un enlace de YouTube (`videoUrl`). Los enlaces son de ejemplo y pueden reemplazarse por el contenido oficial de la marca.

**Persistencia local**
- **Room (SQLite):** usuarios y recetas guardadas.
- **DataStore:** preferencia de accesibilidad (texto grande).
- Operaciones de crear, leer, actualizar y eliminar cuenta.

**Animaciones**
- Transiciones entre pantallas, aparición animada de errores y mensajes, cambio de color animado en botones y `Crossfade` en imágenes.

**Recursos nativos del dispositivo**
- **Cámara** (`TakePicture` + `FileProvider`) con solicitud de permiso en tiempo de ejecución.
- **Galería** de imágenes para la foto de perfil.
- **Ubicación aproximada** (`ACCESS_COARSE_LOCATION`) con permiso en tiempo de ejecución.
- Los permisos se prueban sin capturar datos reales.

## Arquitectura

```
app/src/main/java/com/example/martinifoodlabs_grupo3/
├── data/         Room (AppDatabase, DAOs) y DataStore
├── model/        Entidades y modelos (Usuario, Receta, RecetaGuardada)
├── repository/   Acceso a datos (UsuarioRepository, RecetaRepository)
├── viewmodel/    Estado y lógica (AuthViewModel, Validadores, etc.)
├── navigation/   Rutas y eventos de navegación
└── ui/
    ├── components/  Componentes reutilizables (CampoTexto, RecetaCard, ...)
    ├── screens/     Pantallas
    ├── theme/       Colores, tipografía y tema
    └── utils/       Utilidades de tamaño de ventana
```

Patrón: **MVVM** (UI → ViewModel → Repository → Room/DataStore).

## Tecnologías

- Kotlin 2.0.21 · Jetpack Compose (Material 3)
- Navigation Compose 2.8.4 · Lifecycle ViewModel 2.8.7
- Room 2.6.1 (con KSP) · DataStore Preferences 1.1.1
- Android Gradle Plugin 8.13.2 · compileSdk 36 · minSdk 24

## Cómo ejecutar el proyecto

1. Instala **Android Studio** (versión reciente compatible con AGP 8.13) con JDK 11 o superior.
2. Clona el repositorio:
   ```bash
   git clone https://github.com/Fab-Mondaca/Proyecto_DesarrolloAppMovil.git
   ```
3. En Android Studio, abre la carpeta `Proyecto_DesarrolloAppMovil` (la que contiene `settings.gradle.kts`).
4. Espera a que termine la sincronización de Gradle.
5. Crea o selecciona un emulador (API 24 o superior) o conecta un dispositivo físico.
6. Pulsa **Run ▶**.

### Probar los recursos nativos en el emulador

- **Cámara:** en el emulador usa la cámara virtual; acepta el permiso cuando se solicite.
- **Ubicación:** en *Extended controls → Location* fija una ubicación y pulsa *Send*; luego usa "Ver mi ubicación".

## Flujo de prueba sugerido

1. Crear cuenta con datos ficticios (RUT de ejemplo: `12.345.678-5`; teléfono: `+56912345678`).
2. Iniciar sesión.
3. Explorar recetas, abrir un detalle, tocar "Ver video técnico" (requiere internet) y guardarla.
4. Revisar "Guardadas".
5. En Perfil: elegir foto de galería, tomar una foto y ver la ubicación; editar datos.
6. En Ajustes: activar el texto grande y comprobar que se mantiene al reiniciar la app.

## Planificación

La planificación del proyecto está en el tablero de tareas: _(agregar aquí el enlace)_

El tablero agrupa las tareas en *Hecho*, *En progreso* y *Por hacer (EFT)*.

## Mejoras previstas para la EFT

- Reproductor de video integrado con contenido oficial de Master Martini.
- Backend para compartir cuentas y contenidos entre dispositivos.
- Contraseñas con hash y sal.
- Tests unitarios de los validadores.
- Encuesta de intereses del usuario e integración con la plataforma web.

## Limitaciones del MVP

- Los videos técnicos son enlaces de ejemplo a YouTube (no hay reproductor integrado en la app).
- Los contenidos de recetas son de ejemplo y están definidos en código.
- La contraseña se guarda localmente sin cifrado adicional; en una versión productiva debe usarse hash y un backend.
- No hay backend: todos los datos se guardan localmente en el dispositivo.
- No hay integración con la plataforma web de la organización (prevista como etapa futura).
