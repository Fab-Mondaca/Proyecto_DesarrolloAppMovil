package com.example.martinifoodlabs_grupo3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.martinifoodlabs_grupo3.data.AjustesDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/** Guía 12: estado persistente (DataStore) + loader + feedback animado. */
class AjustesViewModel(app: Application) : AndroidViewModel(app) {
    private val store = AjustesDataStore(app)

    // null = cargando
    private val _textoGrande = MutableStateFlow<Boolean?>(null)
    val textoGrande: StateFlow<Boolean?> = _textoGrande.asStateFlow()

    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje.asStateFlow()

    init {
        viewModelScope.launch {
            delay(700) // simula carga para mostrar el loader
            _textoGrande.value = store.obtenerTextoGrande().first() ?: false
        }
    }

    fun alternar() {
        viewModelScope.launch {
            val nuevo = !(_textoGrande.value ?: false)
            store.guardarTextoGrande(nuevo)
            _textoGrande.value = nuevo
            _mostrarMensaje.value = true
            delay(2000)
            _mostrarMensaje.value = false
        }
    }
}