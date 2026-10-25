package com.example.martinifoodlabs_grupo3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.martinifoodlabs_grupo3.navigation.Screen
import com.example.martinifoodlabs_grupo3.viewmodel.AuthViewModel
import com.example.martinifoodlabs_grupo3.viewmodel.MainViewModel

/** Guía 11 */
@Composable
fun ResumenScreen(authVm: AuthViewModel, mainVm: MainViewModel) {
    val u by authVm.ultimoRegistro.collectAsState()
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier.widthIn(max = 480.dp).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Filled.CheckCircle, null, Modifier.size(72.dp), tint = MaterialTheme.colorScheme.secondary)
            Text("¡Registro exitoso!", style = MaterialTheme.typography.titleLarge)
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Nombre: ${u?.nombre ?: ""}", style = MaterialTheme.typography.bodyLarge)
                    Text("Correo: ${u?.correo ?: ""}", style = MaterialTheme.typography.bodyLarge)
                    Text("Teléfono: ${u?.telefono ?: ""}", style = MaterialTheme.typography.bodyLarge)
                    Text("RUT empresa: ${u?.rutEmpresa ?: ""}", style = MaterialTheme.typography.bodyLarge)
                    Text("Negocio: ${u?.tipoNegocio ?: ""}", style = MaterialTheme.typography.bodyLarge)
                    Text("Contraseña: ••••••", style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = { mainVm.navigateTo(Screen.Login, popUpToRoute = Screen.Login, inclusive = true) },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) { Text("Ir a iniciar sesión") }
        }
    }
}