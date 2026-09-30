package com.example.martinifoodlabs_grupo3.navigation

sealed class Screen(val route: String, val titulo: String) {
    data object Splash : Screen("splash", "Splash")
    data object Login : Screen("login", "Ingreso")
    data object Encuesta : Screen("encuesta", "Encuesta")
    data object Home : Screen("home", "Inicio")
    data object Sede : Screen("sede", "Sede")
    data object Perfil : Screen("perfil", "Perfil")
    data object Recetas : Screen("recetas", "Recetas")
    data object Videos : Screen("videos", "Videos")
    data object Cursos : Screen("cursos", "Cursos")
    data object Detalle : Screen("detalle", "Detalle")
    data object Favoritos : Screen("favoritos", "Favoritos")
}
