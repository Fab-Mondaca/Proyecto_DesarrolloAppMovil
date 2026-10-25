package com.example.martinifoodlabs_grupo3.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.martinifoodlabs_grupo3.model.Usuario

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: Usuario)                       // Create

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): Usuario?        // Read

    @Update
    suspend fun actualizar(usuario: Usuario)                     // Update

    @Delete
    suspend fun eliminar(usuario: Usuario)                       // Delete
}
