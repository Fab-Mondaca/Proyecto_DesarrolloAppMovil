package com.example.martinifoodlabs_grupo3.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Imagen circular desde Uri; si no hay, ícono de perfil por defecto (Guía 13). */
@Composable
fun ImagenInteligente(uri: Uri?, modifier: Modifier = Modifier, tamano: Dp = 140.dp) {
    val context = LocalContext.current
    val bitmap by produceState<Bitmap?>(initialValue = null, key1 = uri) {
        value = if (uri == null) null else withContext(Dispatchers.IO) {
            runCatching {
                context.contentResolver.openInputStream(uri)?.use {
                    BitmapFactory.decodeStream(it, null, BitmapFactory.Options().apply { inSampleSize = 4 })
                }
            }.getOrNull()
        }
    }
    Crossfade(targetState = bitmap, label = "imagen", modifier = modifier) { b ->
        if (b != null) {
            Image(
                b.asImageBitmap(), "Foto de perfil",
                Modifier.size(tamano).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                Icons.Filled.AccountCircle, "Sin foto de perfil",
                Modifier.size(tamano), tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}