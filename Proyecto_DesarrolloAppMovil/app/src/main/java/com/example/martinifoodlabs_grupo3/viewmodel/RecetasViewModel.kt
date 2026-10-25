package com.example.martinifoodlabs_grupo3.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.martinifoodlabs_grupo3.model.Receta
import com.example.martinifoodlabs_grupo3.repository.RecetaRepository

class RecetasViewModel : ViewModel() {
    var consulta by mutableStateOf("")
        private set
    var categoria by mutableStateOf<String?>(null)
        private set

    val filtradas: List<Receta>
        get() = RecetaRepository.recetas.filter {
            (categoria == null || it.categoria == categoria) &&
                    it.titulo.contains(consulta.trim(), ignoreCase = true)
        }

    fun onConsulta(v: String) { consulta = v }
    fun onCategoria(c: String?) { categoria = if (categoria == c) null else c }
}