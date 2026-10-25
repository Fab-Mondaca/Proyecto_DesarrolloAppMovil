package com.example.martinifoodlabs_grupo3.repository

import com.example.martinifoodlabs_grupo3.data.GuardadaDao
import com.example.martinifoodlabs_grupo3.data.UsuarioDao
import com.example.martinifoodlabs_grupo3.model.Usuario
import java.security.MessageDigest

class UsuarioRepository(
    private val dao: UsuarioDao,
    private val guardadaDao: GuardadaDao
) {
    /** false si el correo ya existe */
    suspend fun registrar(usuario: Usuario): Boolean {
        if (dao.buscarPorCorreo(usuario.correo) != null) return false
        dao.insertar(usuario.copy(password = hash(usuario.password)))
        return true
    }

    suspend fun login(correo: String, password: String): Usuario? {
        val u = dao.buscarPorCorreo(correo.trim().lowercase()) ?: return null
        return if (u.password == hash(password)) u else null
    }

    suspend fun actualizar(usuario: Usuario) = dao.actualizar(usuario)

    suspend fun eliminar(usuario: Usuario) {
        guardadaDao.eliminarPorUsuario(usuario.id)
        dao.eliminar(usuario)
    }

    private fun hash(texto: String): String =
        MessageDigest.getInstance("SHA-256").digest(texto.toByteArray())
            .joinToString("") { "%02x".format(it) }
}