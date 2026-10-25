import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import TU.PAQUETE.R
import TU.PAQUETE.navigation.Screen
import TU.PAQUETE.ui.components.BotonGrande
import TU.PAQUETE.ui.components.PantallaBase
import TU.PAQUETE.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(vm: MainViewModel) {
    // Espera 1,5 s y pasa al login, sacando el splash de la pila.
    LaunchedEffect(Unit) {
        delay(1500)
        vm.navigateTo(Screen.Login, popUpTo = Screen.Splash, inclusive = true)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Master Martini",
                modifier = Modifier.size(180.dp)
            )
            Text(
                text = "Master Martini",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun LoginScreen(vm: MainViewModel) {
    PantallaBase(
        titulo = "Ingreso y registro",
        descripcion = "Aquí irá el formulario validado (semana 7)."
    ) {
        BotonGrande("Continuar") { vm.navigateTo(Screen.Encuesta) }
    }
}

@Composable
fun EncuestaScreen(vm: MainViewModel) {
    PantallaBase(
        titulo = "Cuéntanos de tu negocio",
        descripcion = "Aquí irá la encuesta de intereses (semana 7)."
    ) {
        // Al entrar al Inicio se limpian Login y Encuesta de la pila.
        BotonGrande("Guardar y entrar") {
            vm.navigateTo(Screen.Home, popUpTo = Screen.Login, inclusive = true)
        }
        BotonGrande("Volver", principal = false) { vm.navigateBack() }
    }
}

@Composable
fun HomeScreen(vm: MainViewModel) {
    PantallaBase(
        titulo = "Bienvenido a Master Martini",
        descripcion = "Recetas, videos técnicos y cursos para tu negocio."
    ) {
        BotonGrande("Recetas") { vm.navigateTo(Screen.Recetas) }
        BotonGrande("Videos") { vm.navigateTo(Screen.Videos) }
        BotonGrande("Cursos") { vm.navigateTo(Screen.Cursos) }
        BotonGrande("Mis favoritos", principal = false) { vm.navigateTo(Screen.Favoritos) }
    }
}

@Composable
fun SedeScreen() {
    PantallaBase(
        titulo = "Sede y contacto",
        descripcion = "Aquí se usará la ubicación del dispositivo (semana 9)."
    )
}

@Composable
fun PerfilScreen() {
    PantallaBase(
        titulo = "Mi perfil",
        descripcion = "Aquí se usará la cámara para la foto de perfil (semana 9)."
    )
}

// Recetas, Videos y Cursos comparten la misma estructura.
@Composable
private fun ListaBase(titulo: String, descripcion: String, vm: MainViewModel) {
    PantallaBase(titulo = titulo, descripcion = descripcion) {
        BotonGrande("Ver detalle") { vm.navigateTo(Screen.Detalle) }
        BotonGrande("Volver", principal = false) { vm.navigateBack() }
    }
}

@Composable
fun RecetasScreen(vm: MainViewModel) =
    ListaBase("Recetas", "Aquí irá la lista de recetas.", vm)

@Composable
fun VideosScreen(vm: MainViewModel) =
    ListaBase("Videos técnicos", "Aquí irá la lista de videos.", vm)

@Composable
fun CursosScreen(vm: MainViewModel) =
    ListaBase("Cursos de especialidad", "Aquí irá la lista de cursos.", vm)

@Composable
fun DetalleScreen(vm: MainViewModel) {
    PantallaBase(
        titulo = "Detalle",
        descripcion = "Aquí irá el detalle y el botón de favorito animado."
    ) {
        BotonGrande("Guardar en favoritos") { /* semana 8: guardar en Room */ }
        BotonGrande("Volver", principal = false) { vm.navigateBack() }
    }
}

@Composable
fun FavoritosScreen(vm: MainViewModel) {
    PantallaBase(
        titulo = "Mis favoritos",
        descripcion = "Aquí irán los guardados en Room (semana 8)."
    ) {
        BotonGrande("Volver", principal = false) { vm.navigateBack() }
    }
}