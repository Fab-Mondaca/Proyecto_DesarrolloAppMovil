package com.example.martinifoodlabs_grupo3.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.martinifoodlabs_grupo3.model.RecetaGuardada
import kotlinx.coroutines.flow.Flow

@Dao
interface GuardadaDao {
    @Insert
    suspend fun insertar(g: RecetaGuardada)                                   // Create

    @Query("SELECT * FROM guardadas WHERE usuarioId = :u ORDER BY id DESC")
    fun listar(u: Int): Flow<List<RecetaGuardada>>                           // Read (reactivo)

    @Query("SELECT COUNT(*) FROM guardadas WHERE usuarioId = :u AND recetaId = :r")
    suspend fun existe(u: Int, r: Int): Int

    @Query("UPDATE guardadas SET nota = :nota WHERE id = :id")
    suspend fun actualizarNota(id: Int, nota: String)                        // Update

    @Query("DELETE FROM guardadas WHERE id = :id")
    suspend fun eliminar(id: Int)                                            // Delete

    @Query("DELETE FROM guardadas WHERE usuarioId = :u")
    suspend fun eliminarPorUsuario(u: Int)
}