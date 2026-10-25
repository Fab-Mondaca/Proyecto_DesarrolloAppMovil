# MartiniFoodLabs_Grupo3 — Master Martini Chile SPA (DSY1105)

MVP académico (datos ficticios): recetas, videos y cursos técnicos para clientes y prospectos.
Kotlin + Jetpack Compose + Material 3 + MVVM + Room.

## Integrantes
- Fabian Mondaca

## Funcionalidades
- Login y registro con validaciones por campo (íconos + mensajes animados): nombre, correo, teléfono, RUT (módulo 11), tipo de negocio, contraseña.
- Persistencia local con Room/SQLite: CRUD de recetas guardadas (nota editable) y de la cuenta (editar y eliminar); contraseña con SHA-256.
- DataStore: preferencia «texto grande» para accesibilidad, con loader inicial.
- Registro con checkbox de términos y pantalla Resumen (ViewModel compartido).
- Menú lateral (drawer), barra superior e inferior.
- Navegación: NavHost + eventos desde MainViewModel, barra inferior (Inicio / Recetas / Perfil), detalle con argumento.
- Diseño adaptable (WindowSizeClass: 1/2/3 columnas), tipografía grande y paleta bohemia.
- Recursos nativos: cámara (TakePicture + FileProvider), galería (GetContent) y ubicación aproximada, con solicitud de permisos.
- Animaciones: entrada del logo, errores, transiciones entre pantallas, Crossfade de foto.

## Estructura
`model/ data/ repository/ viewmodel/ navigation/ ui/{theme,components,screens,utils}`