package com.example.martinifoodlabs_grupo3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.martinifoodlabs_grupo3.ui.AppRoot
import com.example.martinifoodlabs_grupo3.ui.theme.MartiniFoodLabs_Grupo3Theme
import com.example.martinifoodlabs_grupo3.viewmodel.AjustesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val ajustesVm: AjustesViewModel = viewModel()
            val grande by ajustesVm.textoGrande.collectAsState()
            val densidad = LocalDensity.current

            MartiniFoodLabs_Grupo3Theme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val g = grande
                    if (g == null) {
                        // Loader inicial mientras se lee DataStore (Guía 12)
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        val escala = if (g) densidad.fontScale * 1.3f else densidad.fontScale
                        CompositionLocalProvider(LocalDensity provides Density(densidad.density, escala)) {
                            AppRoot(ajustesVm)
                        }
                    }
                }
            }
        }
    }
}

