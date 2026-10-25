package com.example.martinifoodlabs_grupo3.model

data class Receta(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val duracion: String,
    val descripcion: String,
    val pasos: List<String>
)
