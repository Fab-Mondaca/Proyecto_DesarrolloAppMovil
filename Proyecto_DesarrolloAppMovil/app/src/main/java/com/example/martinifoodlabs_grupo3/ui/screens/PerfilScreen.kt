package com.example.martinifoodlabs_grupo3.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.martinifoodlabs_grupo3.navigation.Screen
import com.example.martinifoodlabs_grupo3.ui.components.CampoTexto
import com.example.martinifoodlabs_grupo3.ui.components.ImagenInteligente
import com.example.martinifoodlabs_grupo3.viewmodel.AuthViewModel
import com.example.martinifoodlabs_grupo3.viewmodel.MainViewModel
import com.example.martinifoodlabs_grupo3.viewmodel.PerfilViewModel
import java.io.File

private fun crearUriFoto(context: Context): Uri {
    val dir = File(context.cacheDir, "images").apply { mkdirs() }
    val archivo = File.createTempFile("foto_", ".jpg", dir)
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", archivo)
}

@SuppressLint("MissingPermission")
private fun ubicacionReciente(context: Context): Location? {
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.getProviders(true).mapNotNull { lm.getLastKnownLocation(it) }.maxByOrNull { it.time }
}

private fun textoUbicacion(l: Location?): String =
    l?.let { "Lat %.4f, Lng %.4f".format(it.latitude, it.longitude) }
        ?: "Ubicación no disponible (activa el GPS o simula una en el emulador)"

@Composable
fun PerfilScreen(authVm: AuthViewModel, mainVm: MainViewModel, perfilVm: PerfilViewModel) {
    val usuario by authVm.usuario.collectAsState()
    val edicion by authVm.edicion.collectAsState()
    val imagen by perfilVm.imagenUri.collectAsState()
    val context = LocalContext.current

    var mensaje by remember { mutableStateOf<String?>(null) }
    var ubicacion by remember { mutableStateOf<String?>(null) }
    var confirmarEliminar by remember { mutableStateOf(false) }

    // Recurso nativo 1: galería
    val galeria = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { perfilVm.onGaleria(it) }

    // Recurso nativo 2: cámara (TakePicture + FileProvider)
    val camara = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { perfilVm.onCamara(it) }
    fun abrirCamara() {
        val uri = crearUriFoto(context)
        perfilVm.prepararCamara(uri)
        camara.launch(uri)
    }
    val permisoCamara = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { ok ->
        if (ok) abrirCamara() else mensaje = "Permiso de cámara denegado"
    }

    // Recurso nativo 3: ubicación aproximada
    val permisoUbicacion = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { ok ->
        if (ok) ubicacion = textoUbicacion(ubicacionReciente(context)) else mensaje = "Permiso de ubicación denegado"
    }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ImagenInteligente(uri = imagen)
        Text(usuario?.nombre ?: "", style = MaterialTheme.typography.titleLarge)
        Text(usuario?.correo ?: "", style = MaterialTheme.typography.bodyLarge)
        Text("${usuario?.tipoNegocio ?: ""} · RUT ${usuario?.rutEmpresa ?: ""}", style = MaterialTheme.typography.bodyMedium)

        val anchoBoton = Modifier.fillMaxWidth().widthIn(max = 420.dp).height(56.dp)

        Button(onClick = { galeria.launch("image/*") }, modifier = anchoBoton) {
            Icon(Icons.Filled.PhotoLibrary, null); Spacer(Modifier.width(8.dp)); Text("Elegir de la galería")
        }
        Button(
            onClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                    abrirCamara()
                else permisoCamara.launch(Manifest.permission.CAMERA)
            },
            modifier = anchoBoton
        ) { Icon(Icons.Filled.CameraAlt, null); Spacer(Modifier.width(8.dp)); Text("Tomar foto") }

        OutlinedButton(
            onClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    ubicacion = textoUbicacion(ubicacionReciente(context))
                else permisoUbicacion.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            },
            modifier = anchoBoton
        ) { Icon(Icons.Filled.LocationOn, null); Spacer(Modifier.width(8.dp)); Text("Ver mi ubicación") }

        AnimatedVisibility(ubicacion != null) { Text(ubicacion ?: "", style = MaterialTheme.typography.bodyLarge) }
        AnimatedVisibility(mensaje != null) {
            Text(mensaje ?: "", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyLarge)
        }

        HorizontalDivider()
        OutlinedButton(onClick = { authVm.abrirEdicion() }, modifier = anchoBoton) {
            Icon(Icons.Filled.Edit, null); Spacer(Modifier.width(8.dp)); Text("Editar mis datos")
        }
        TextButton(onClick = {
            authVm.cerrarSesion()
            mainVm.navigateTo(Screen.Login, popUpToRoute = Screen.Home, inclusive = true)
        }) {
            Icon(Icons.AutoMirrored.Filled.Logout, null); Spacer(Modifier.width(8.dp))
            Text("Cerrar sesión", style = MaterialTheme.typography.bodyLarge)
        }
        TextButton(onClick = { confirmarEliminar = true }) {
            Icon(Icons.Filled.DeleteForever, null, tint = MaterialTheme.colorScheme.error); Spacer(Modifier.width(8.dp))
            Text("Eliminar mi cuenta", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyLarge)
        }
    }

    // Update
    edicion?.let { e ->
        AlertDialog(
            onDismissRequest = { authVm.cerrarEdicion() },
            title = { Text("Editar datos") },
            text = {
                Column {
                    CampoTexto(e.nombre.valor, authVm::onEdNombre, "Nombre", Icons.Filled.Edit, e.nombre.error)
                    CampoTexto(e.telefono.valor, authVm::onEdTelefono, "Teléfono", Icons.Filled.Edit, e.telefono.error, teclado = KeyboardType.Phone)
                }
            },
            confirmButton = { TextButton(onClick = { authVm.guardarEdicion() }) { Text("Guardar") } },
            dismissButton = { TextButton(onClick = { authVm.cerrarEdicion() }) { Text("Cancelar") } }
        )
    }

    // Delete
    if (confirmarEliminar) {
        AlertDialog(
            onDismissRequest = { confirmarEliminar = false },
            title = { Text("¿Eliminar cuenta?") },
            text = { Text("Se borrarán tus datos y recetas guardadas de este dispositivo.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarEliminar = false
                    authVm.eliminarCuenta {
                        mainVm.navigateTo(Screen.Login, popUpToRoute = Screen.Home, inclusive = true)
                    }
                }) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = { TextButton(onClick = { confirmarEliminar = false }) { Text("Cancelar") } }
        )
    }
}
