package com.example.martinifoodlabs_grupo3.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.martinifoodlabs_grupo3.navigation.NavigationEvent
import com.example.martinifoodlabs_grupo3.navigation.Screen
import com.example.martinifoodlabs_grupo3.ui.screens.*
import com.example.martinifoodlabs_grupo3.ui.utils.obtenerWindowSizeClass
import com.example.martinifoodlabs_grupo3.viewmodel.*
import kotlinx.coroutines.launch

private data class ItemNav(val screen: Screen, val etiqueta: String, val icono: ImageVector)

private val itemsBarra = listOf(
    ItemNav(Screen.Home, "Inicio", Icons.Filled.Home),
    ItemNav(Screen.Recetas, "Recetas", Icons.Filled.MenuBook),
    ItemNav(Screen.Guardadas, "Guardadas", Icons.Filled.Bookmark),
    ItemNav(Screen.Perfil, "Perfil", Icons.Filled.AccountCircle)
)
private val itemsMenu = itemsBarra + ItemNav(Screen.Ajustes, "Ajustes", Icons.Filled.Settings)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoot(
    ajustesVm: AjustesViewModel,
    mainVm: MainViewModel = viewModel(),
    authVm: AuthViewModel = viewModel(),
    recetasVm: RecetasViewModel = viewModel(),
    guardadasVm: GuardadasViewModel = viewModel(),
    perfilVm: PerfilViewModel = viewModel()
) {
    val nav = rememberNavController()
    val ancho = obtenerWindowSizeClass().widthSizeClass
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Eventos de navegación emitidos por el ViewModel (Guía 10)
    LaunchedEffect(Unit) {
        mainVm.navigationEvents.collect { e ->
            when (e) {
                is NavigationEvent.NavigateTo -> nav.navigate(e.route.route) {
                    e.popUpToRoute?.let { p -> popUpTo(p.route) { inclusive = e.inclusive } }
                    launchSingleTop = e.singleTop
                }
                is NavigationEvent.PopBackStack -> nav.popBackStack()
                is NavigationEvent.NavigateUp -> nav.navigateUp()
            }
        }
    }

    // El CRUD de guardadas depende del usuario en sesión
    val usuario by authVm.usuario.collectAsState()
    LaunchedEffect(usuario) { guardadasVm.setUsuario(usuario?.id ?: -1) }

    val entrada by nav.currentBackStackEntryAsState()
    val ruta = entrada?.destination?.route
    val rutasMenu = itemsMenu.map { it.screen.route }
    val enBarra = itemsBarra.map { it.screen.route }
    val esDetalle = ruta == Screen.Detalle.PATTERN

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = ruta in rutasMenu,
        drawerContent = {
            ModalDrawerSheet {
                Text("Master Martini", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
                itemsMenu.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.etiqueta) },
                        icon = { Icon(item.icono, null) },
                        selected = ruta == item.screen.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            mainVm.navigateTo(item.screen, popUpToRoute = Screen.Home, singleTop = true)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    icon = { Icon(Icons.AutoMirrored.Filled.Logout, null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        authVm.cerrarSesion()
                        mainVm.navigateTo(Screen.Login, popUpToRoute = Screen.Home, inclusive = true)
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (ruta in rutasMenu || esDetalle) {
                    TopAppBar(
                        title = {
                            Text(
                                when (ruta) {
                                    Screen.Home.route -> "Master Martini"
                                    Screen.Recetas.route -> "Recetas y cursos"
                                    Screen.Guardadas.route -> "Guardadas"
                                    Screen.Perfil.route -> "Mi perfil"
                                    Screen.Ajustes.route -> "Ajustes"
                                    else -> "Detalle"
                                }
                            )
                        },
                        navigationIcon = {
                            if (esDetalle) {
                                IconButton(onClick = { mainVm.navigateBack() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                                }
                            } else {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Filled.Menu, "Abrir menú")
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            },
            bottomBar = {
                if (ruta in enBarra) {
                    NavigationBar {
                        itemsBarra.forEach { item ->
                            NavigationBarItem(
                                selected = ruta == item.screen.route,
                                onClick = { mainVm.navigateTo(item.screen, popUpToRoute = Screen.Home, singleTop = true) },
                                icon = { Icon(item.icono, null) },
                                label = { Text(item.etiqueta) }
                            )
                        }
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = nav,
                startDestination = Screen.Login.route,
                modifier = Modifier.padding(padding),
                enterTransition = { fadeIn() + slideInHorizontally { it / 6 } },
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() + slideOutHorizontally { it / 6 } }
            ) {
                composable(Screen.Login.route) { LoginScreen(authVm, mainVm) }
                composable(Screen.Registro.route) { RegistroScreen(authVm, mainVm) }
                composable(Screen.Resumen.route) { ResumenScreen(authVm, mainVm) }
                composable(Screen.Home.route) { HomeScreen(authVm, mainVm, ancho) }
                composable(Screen.Recetas.route) { RecetasScreen(recetasVm, mainVm, ancho) }
                composable(Screen.Guardadas.route) { GuardadasScreen(guardadasVm, mainVm) }
                composable(Screen.Perfil.route) { PerfilScreen(authVm, mainVm, perfilVm) }
                composable(Screen.Ajustes.route) { AjustesScreen(ajustesVm) }
                composable(
                    Screen.Detalle.PATTERN,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { DetalleScreen(it.arguments?.getInt("id") ?: 0, guardadasVm) }
            }
        }
    }
}
