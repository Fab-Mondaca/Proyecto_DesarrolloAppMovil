package com.example.martinifoodlabs_grupo3.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Guía 13. */
class PerfilViewModel : ViewModel() {
    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    private var pendienteCamara: Uri? = null

    fun onGaleria(uri: Uri?) { if (uri != null) _imagenUri.value = uri } // null = cancelado
    fun prepararCamara(uri: Uri) { pendienteCamara = uri }
    fun onCamara(exito: Boolean) { if (exito) _imagenUri.value = pendienteCamara }
}