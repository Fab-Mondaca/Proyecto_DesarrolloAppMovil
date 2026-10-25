package com.example.martinifoodlabs_grupo3.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.martinifoodlabs_grupo3.ui.components.CampoTexto
import com.example.martinifoodlabs_grupo3.navigation.Screen
import com.example.martinifoodlabs_grupo3.viewmodel.AuthViewModel
import com.example.martinifoodlabs_grupo3.viewmodel.MainViewModel

@Composable
fun LoginScreen(authVm: AuthViewModel, mainVm: MainViewModel) {
    val s by authVm.login.collectAsState()
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AnimatedVisibility(visible, enter = fadeIn() + scaleIn()) {
                Icon(Icons.Filled.Restaurant, null, Modifier.size(72.dp), tint = MaterialTheme.colorScheme.primary)
            }
            Text("Master Martini", style = MaterialTheme.typography.titleLarge)
            Text("Recetas, videos y cursos técnicos", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(16.dp))

            CampoTexto(s.correo.valor, authVm::onLoginCorreo, "Correo", Icons.Filled.Email, s.correo.error, teclado = KeyboardType.Email)
            CampoTexto(s.password.valor, authVm::onLoginPassword, "Contraseña", Icons.Filled.Lock, s.password.error, esPassword = true)

            AnimatedVisibility(s.errorGeneral != null) {
                Text(s.errorGeneral ?: "", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyLarge)
            }

            Button(
                onClick = {
                    authVm.iniciarSesion {
                        mainVm.navigateTo(Screen.Home, popUpToRoute = Screen.Login, inclusive = true)
                    }
                },
                enabled = !s.cargando,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) { Text(if (s.cargando) "Ingresando..." else "Ingresar") }

            TextButton(onClick = { mainVm.navigateTo(Screen.Registro) }) {
                Text("¿No tienes cuenta? Regístrate", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
