package com.example.martinifoodlabs_grupo3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinifoodlabs_grupo3.data.AppDatabase
import com.example.martinifoodlabs_grupo3.model.RecetaGuardada
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** CRUD sobre SQLite (Room): guardar, listar, editar nota, eliminar. */
@OptIn(ExperimentalCoroutinesApi::class)
class GuardadasViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.get(app).guardadaDao()
    private val usuarioId = MutableStateFlow(-1)

    val guardadas: StateFlow<List<RecetaGuardada>> = usuarioId
        .flatMapLatest { dao.listar(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private val _editando = MutableStateFlow<RecetaGuardada?>(null)
    val editando: StateFlow<RecetaGuardada?> = _editando.asStateFlow()

    private val _notaTexto = MutableStateFlow("")
    val notaTexto: StateFlow<String> = _notaTexto.asStateFlow()

    fun setUsuario(id: Int) { usuarioId.value = id }
    fun limpiarMensaje() { _mensaje.value = null }

    // Create
    fun guardar(recetaId: Int) {
        val u = usuarioId.value
        if (u < 0) return
        viewModelScope.launch {
            if (dao.existe(u, recetaId) > 0) {
                _mensaje.value = "Ya está en tus guardadas"
            } else {
                dao.insertar(RecetaGuardada(usuarioId = u, recetaId = recetaId))
                _mensaje.value = "Receta guardada"
            }
        }
    }

    // Update
    fun abrirEdicion(g: RecetaGuardada) { _editando.value = g; _notaTexto.value = g.nota }
    fun onNota(t: String) { if (t.length <= 120) _notaTexto.value = t }
    fun cerrarEdicion() { _editando.value = null }
    fun guardarNota() {
        val g = _editando.value ?: return
        viewModelScope.launch {
            dao.actualizarNota(g.id, _notaTexto.value.trim())
            _editando.value = null
        }
    }

    // Delete
    fun eliminar(g: RecetaGuardada) { viewModelScope.launch { dao.eliminar(g.id) } }
}