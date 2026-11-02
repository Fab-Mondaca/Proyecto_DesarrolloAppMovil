package com.example.martinifoodlabs_grupo3.model

import java.net.URLEncoder

data class Receta(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val duracion: String,
    val descripcion: String,
    val pasos: List<String>,
    // URL del video técnico (simulado con enlace). Reemplazar por el video oficial de Master Martini.
    val videoUrl: String =
        "https://youtu.be/594j3Mk4gRQ" + URLEncoder.encode("$titulo receta", "UTF-8")
)

