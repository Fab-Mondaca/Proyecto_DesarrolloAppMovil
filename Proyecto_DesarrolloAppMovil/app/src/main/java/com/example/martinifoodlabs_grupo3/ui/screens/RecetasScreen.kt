package com.example.martinifoodlabs_grupo3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.martinifoodlabs_grupo3.navigation.Screen
import com.example.martinifoodlabs_grupo3.repository.RecetaRepository
import com.example.martinifoodlabs_grupo3.ui.components.RecetaCard
import com.example.martinifoodlabs_grupo3.ui.utils.columnasPara
import com.example.martinifoodlabs_grupo3.viewmodel.MainViewModel
import com.example.martinifoodlabs_grupo3.viewmodel.RecetasViewModel

@Composable
fun RecetasScreen(vm: RecetasViewModel, mainVm: MainViewModel, ancho: WindowWidthSizeClass) {
    val lista = vm.filtradas

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnasPara(ancho)),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = vm.consulta,
                    onValueChange = vm::onConsulta,
                    label = { Text("Buscar receta") },
                    leadingIcon = { Icon(Icons.Filled.Search, null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(RecetaRepository.categorias) { c ->
                        FilterChip(
                            selected = vm.categoria == c,
                            onClick = { vm.onCategoria(c) },
                            label = { Text(c) }
                        )
                    }
                }
            }
        }
        if (lista.isEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text("No hay recetas que coincidan.", style = MaterialTheme.typography.bodyLarge)
            }
        }
        items(lista, key = { it.id }) { r ->
            RecetaCard(r, onClick = { mainVm.navigateTo(Screen.Detalle(r.id)) })
        }
    }
}