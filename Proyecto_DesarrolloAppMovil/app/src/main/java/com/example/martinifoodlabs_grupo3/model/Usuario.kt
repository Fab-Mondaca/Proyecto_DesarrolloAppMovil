package com.example.martinifoodlabs_grupo3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Datos de ejemplo (restricción del caso)
@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val telefono: String,
    val rutEmpresa: String,
    val tipoNegocio: String,
    val password: String
)
 