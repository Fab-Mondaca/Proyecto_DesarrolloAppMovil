package com.example.martinifoodlabs_grupo3.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.example.martinifoodlabs_grupo3.repository.RecetaRepository
import com.example.martinifoodlabs_grupo3.viewmodel.GuardadasViewModel
import kotlinx.coroutines.delay

@Composable
fun DetalleScreen(id: Int, vm: GuardadasViewModel) {
    val receta = RecetaRepository.porId(id)
    val uriHandler = LocalUriHandler.current
    var errorVideo by remember { mutableStateOf(false) }
    val mensaje by vm.mensaje.collectAsState()
    LaunchedEffect(mensaje) {
        if (mensaje != null) { delay(2000); vm.limpiarMensaje() }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            Modifier.widthIn(max = 640.dp).verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (receta == null) {
                Text("Receta no encontrada", style = MaterialTheme.typography.titleMedium)
            } else {
                Text(receta.titulo, style = MaterialTheme.typography.titleLarge)
                Text("${receta.categoria} · ${receta.duracion}", color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge)
                Card(
                    onClick = {
                        try { uriHandler.openUri(receta.videoUrl); errorVideo = false }
                        catch (e: Exception) { errorVideo = true }
                    },
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Filled.PlayCircle, "Ver video", Modifier.size(64.dp))
                        Text("Ver video técnico", style = MaterialTheme.typography.titleMedium)
                    }
                }
                AnimatedVisibility(errorVideo) {
                    Text("No se pudo abrir el video. Revisa tu conexión o instala un navegador.",
                        color = MaterialTheme.colorScheme.error)
                }
                Text(receta.descripcion, style = MaterialTheme.typography.bodyLarge)
                Text("Pasos", style = MaterialTheme.typography.titleMedium)
                receta.pasos.forEachIndexed { i, p ->
                    Text("${i + 1}. $p", style = MaterialTheme.typography.bodyLarge)
                }
                Button(
                    onClick = { vm.guardar(receta.id) },
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Icon(Icons.Filled.Bookmark, null); Spacer(Modifier.width(8.dp)); Text("Guardar en mis recetas")
                }
                AnimatedVisibility(mensaje != null) {
                    Text(mensaje ?: "", color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}