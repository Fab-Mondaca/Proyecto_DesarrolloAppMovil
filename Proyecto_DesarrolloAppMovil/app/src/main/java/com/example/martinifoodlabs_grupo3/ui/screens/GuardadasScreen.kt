package com.example.martinifoodlabs_grupo3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.martinifoodlabs_grupo3.navigation.Screen
import com.example.martinifoodlabs_grupo3.repository.RecetaRepository
import com.example.martinifoodlabs_grupo3.viewmodel.GuardadasViewModel
import com.example.martinifoodlabs_grupo3.viewmodel.MainViewModel

@Composable
fun GuardadasScreen(vm: GuardadasViewModel, mainVm: MainViewModel) {
    val lista by vm.guardadas.collectAsState()
    val editando by vm.editando.collectAsState()
    val nota by vm.notaTexto.collectAsState()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            Modifier.widthIn(max = 640.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Text("Mis recetas guardadas", style = MaterialTheme.typography.titleLarge) }
            if (lista.isEmpty()) {
                item {
                    Text("Aún no guardas recetas. Abre una receta y toca «Guardar».",
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
            items(lista, key = { it.id }) { g ->
                val r = RecetaRepository.porId(g.recetaId)
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(r?.titulo ?: "Receta", style = MaterialTheme.typography.titleMedium)
                        Text(if (g.nota.isBlank()) "Sin nota" else "Nota: ${g.nota}", style = MaterialTheme.typography.bodyLarge)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(onClick = { mainVm.navigateTo(Screen.Detalle(g.recetaId)) }) { Text("Ver") }
                            Spacer(Modifier.weight(1f))
                            IconButton(onClick = { vm.abrirEdicion(g) }) { Icon(Icons.Filled.Edit, "Editar nota") }
                            IconButton(onClick = { vm.eliminar(g) }) {
                                Icon(Icons.Filled.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }

    if (editando != null) {
        AlertDialog(
            onDismissRequest = { vm.cerrarEdicion() },
            title = { Text("Nota de la receta") },
            text = {
                OutlinedTextField(
                    value = nota, onValueChange = vm::onNota,
                    label = { Text("Nota (máx. 120)") }, modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = { TextButton(onClick = { vm.guardarNota() }) { Text("Guardar") } },
            dismissButton = { TextButton(onClick = { vm.cerrarEdicion() }) { Text("Cancelar") } }
        )
    }
}
