package com.example.martinifoodlabs_grupo3.navigation

//(Guía 10)
sealed class Screen(val route: String) {
    data object Login : Screen("login_page")
    data object Registro : Screen("registro_page")
    data object Resumen : Screen("resumen_page")
    data object Home : Screen("home_page")
    data object Recetas : Screen("recetas_page")
    data object Guardadas : Screen("guardadas_page")
    data object Perfil : Screen("perfil_page")
    data object Ajustes : Screen("ajustes_page")

    data class Detalle(val id: Int) : Screen("detalle_page/$id") {
        companion object { const val PATTERN = "detalle_page/{id}" }
    }
}