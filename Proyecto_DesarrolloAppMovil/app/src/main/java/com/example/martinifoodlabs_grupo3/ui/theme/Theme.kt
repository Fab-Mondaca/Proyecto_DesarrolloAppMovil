package com.example.martinifoodlabs_grupo3.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val EsquemaBoho = lightColorScheme(
    primary = Terracota,
    onPrimary = Color.White,
    primaryContainer = TerracotaClaro,
    onPrimaryContainer = Cacao,
    secondary = Mostaza,
    onSecondary = Cacao,
    secondaryContainer = MostazaClaro,
    onSecondaryContainer = Cacao,
    tertiary = Salvia,
    onTertiary = Cacao,
    tertiaryContainer = SalviaClaro,
    onTertiaryContainer = Cacao,
    background = Lino,
    onBackground = Cacao,
    surface = Lino,
    onSurface = Cacao,
    surfaceVariant = LinoOscuro,
    onSurfaceVariant = CacaoSuave,
    outline = CacaoSuave,
    error = RojoError,
    onError = Color.White
)

@Composable
fun MartiniFoodLabs_Grupo3Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaBoho,
        typography = Tipografia,
        content = content
    )
}