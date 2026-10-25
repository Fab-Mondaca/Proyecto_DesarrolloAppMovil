package com.example.martinifoodlabs_grupo3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.martinifoodlabs_grupo3.navigation.Screen
import com.example.martinifoodlabs_grupo3.repository.RecetaRepository
import com.example.martinifoodlabs_grupo3.ui.components.RecetaCard
import com.example.martinifoodlabs_grupo3.ui.utils.columnasPara
import com.example.martinifoodlabs_grupo3.viewmodel.AuthViewModel
import com.example.martinifoodlabs_grupo3.viewmodel.MainViewModel

@Composable
fun HomeScreen(authVm: AuthViewModel, mainVm: MainViewModel, ancho: WindowWidthSizeClass) {
    val usuario by authVm.usuario.collectAsState()
    val columnas = columnasPara(ancho)

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnas),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("¡Hola, ${usuario?.nombre?.substringBefore(" ") ?: ""}!", style = MaterialTheme.typography.titleLarge)
                Text("Aprende con recetas y videos técnicos", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(8.dp))
                Text("Destacadas", style = MaterialTheme.typography.titleMedium)
            }
        }
        items(RecetaRepository.recetas.take(3)) { r ->
            RecetaCard(r, onClick = { mainVm.navigateTo(Screen.Detalle(r.id)) })
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Button(
                onClick = { mainVm.navigateTo(Screen.Recetas, popUpToRoute = Screen.Home, singleTop = true) },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) { Text("Ver todas las recetas") }
        }
    }
}