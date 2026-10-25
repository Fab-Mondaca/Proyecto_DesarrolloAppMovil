package com.example.martinifoodlabs_grupo3.model

import androidx.room.Entity
import androidx.room.PrimaryKey


/** Receta guardada por un usuario, con nota personal (CRUD completo). */
@Entity(tableName = "guardadas")
data class RecetaGuardada(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val recetaId: Int,
    val nota: String = ""
)