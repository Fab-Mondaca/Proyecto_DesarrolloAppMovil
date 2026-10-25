package com.example.martinifoodlabs_grupo3.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.martinifoodlabs_grupo3.viewmodel.AjustesViewModel

/** Guía 12. */
@Composable
fun AjustesScreen(vm: AjustesViewModel) {
    val grande by vm.textoGrande.collectAsState()
    val mostrarMensaje by vm.mostrarMensaje.collectAsState()
    val activo = grande == true

    val color by animateColorAsState(
        targetValue = if (activo) Color(0xFF6E8062) else Color(0xFFB5532D),
        animationSpec = tween(500), label = "colorBoton"
    )
    val textoBoton by remember(activo) { derivedStateOf { if (activo) "Desactivar texto grande" else "Activar texto grande" } }
    var verInfo by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Accesibilidad", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("Aumenta el tamaño de las letras en toda la app.", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { vm.alternar() },
            colors = ButtonDefaults.buttonColors(containerColor = color),
            modifier = Modifier.fillMaxWidth().widthIn(max = 480.dp).height(60.dp)
        ) { Text(textoBoton, style = MaterialTheme.typography.labelLarge) }

        Spacer(Modifier.height(16.dp))
        AnimatedVisibility(mostrarMensaje) {
            Text("¡Preferencia guardada!", color = Color(0xFF4C6B3F), style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = { verInfo = !verInfo }) {
            Text(if (verInfo) "Ocultar información" else "¿Qué se guarda?")
        }
        AnimatedVisibility(verInfo) {
            Text(
                "Solo esta preferencia, en el dispositivo (DataStore). No se envían datos.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}