package com.example.martinifoodlabs_grupo3.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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

private val tiposNegocio = listOf("Panadería", "Pastelería", "Chocolatería", "Casino", "Restaurante", "Otro")

@Composable
fun RegistroScreen(authVm: AuthViewModel, mainVm: MainViewModel) {
    val s by authVm.registro.collectAsState()
    var menu by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .widthIn(max = 520.dp)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { mainVm.navigateBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                }
                Text("Crear cuenta", style = MaterialTheme.typography.titleLarge)
            }
            Text("Usa datos ficticios (MVP académico)", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))

            CampoTexto(s.nombre.valor, authVm::onNombre, "Nombre completo", Icons.Filled.Person, s.nombre.error)
            CampoTexto(s.correo.valor, authVm::onCorreo, "Correo", Icons.Filled.Email, s.correo.error, teclado = KeyboardType.Email)
            CampoTexto(s.telefono.valor, authVm::onTelefono, "Teléfono (+56912345678)", Icons.Filled.Phone, s.telefono.error, teclado = KeyboardType.Phone)
            CampoTexto(s.rut.valor, authVm::onRut, "RUT empresa (12.345.678-5)", Icons.Filled.Badge, s.rut.error)

            // Selector de tipo de negocio
            Box {
                OutlinedTextField(
                    value = s.tipoNegocio.valor,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de negocio") },
                    leadingIcon = { Icon(Icons.Filled.Store, null) },
                    trailingIcon = {
                        if (s.tipoNegocio.error != null)
                            Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                        else Icon(Icons.Filled.ArrowDropDown, null)
                    },
                    isError = s.tipoNegocio.error != null,
                    supportingText = { Text(s.tipoNegocio.error ?: "", color = MaterialTheme.colorScheme.error) },
                    modifier = Modifier.fillMaxWidth()
                )
                // Capa transparente para abrir el menú al tocar
                Box(Modifier.matchParentSize().clickableSinRipple { menu = true })
                DropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
                    tiposNegocio.forEach { t ->
                        DropdownMenuItem(text = { Text(t) }, onClick = { authVm.onTipoNegocio(t); menu = false })
                    }
                }
            }

            CampoTexto(s.password.valor, authVm::onPassword, "Contraseña", Icons.Filled.Lock, s.password.error, esPassword = true)
            CampoTexto(s.confirmar.valor, authVm::onConfirmar, "Repetir contraseña", Icons.Filled.Lock, s.confirmar.error, esPassword = true)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = s.aceptaTerminos, onCheckedChange = authVm::onTerminos)
                Text("Acepto los términos y condiciones", style = MaterialTheme.typography.bodyLarge)
            }
            AnimatedVisibility(s.errorTerminos != null) {
                Text(s.errorTerminos ?: "", color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    authVm.registrar {
                        mainVm.navigateTo(Screen.Resumen)
                    }
                },
                enabled = !s.cargando,
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) { Text(if (s.cargando) "Guardando..." else "Registrarme") }
        }
    }
}