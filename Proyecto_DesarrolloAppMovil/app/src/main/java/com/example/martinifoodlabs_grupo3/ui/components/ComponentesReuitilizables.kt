import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import TU.PAQUETE.navigation.Screen
import TU.PAQUETE.viewmodel.MainViewModel

// Pantalla vacía con título, descripción y espacio para botones.
@Composable
fun PantallaBase(
    titulo: String,
    descripcion: String,
    contenido: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Text(
            text = descripcion,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        contenido()
    }
}

// Botón de 56 dp de alto: fácil de tocar para todos los usuarios.
@Composable
fun BotonGrande(
    texto: String,
    principal: Boolean = true,
    onClick: () -> Unit
) {
    val modificador = Modifier
        .fillMaxWidth()
        .height(56.dp)
    if (principal) {
        Button(onClick = onClick, modifier = modificador) { Text(text = texto) }
    } else {
        OutlinedButton(onClick = onClick, modifier = modificador) { Text(text = texto) }
    }
}

// Barra inferior: Inicio, Sede y Perfil.
@Composable
fun BarraInferior(rutaActual: String?, vm: MainViewModel) {
    val items = listOf(
        Screen.Home to Icons.Default.Home,
        Screen.Sede to Icons.Default.Place,
        Screen.Perfil to Icons.Default.Person
    )
    NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceVariant) {
        items.forEach { (screen, icono) ->
            NavigationBarItem(
                selected = rutaActual == screen.route,
                onClick = {
                    vm.navigateTo(screen, popUpTo = Screen.Home, singleTop = true)
                },
                icon = { Icon(imageVector = icono, contentDescription = screen.titulo) },
                label = {
                    Text(text = screen.titulo, style = MaterialTheme.typography.labelLarge)
                }
            )
        }
    }
}
