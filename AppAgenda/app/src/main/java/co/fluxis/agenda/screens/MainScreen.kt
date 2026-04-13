package co.fluxis.agenda.screens

import CierresScreen
import ClientesScreen
import ComisionesScreen
import ProfesionalesScreen
import ReservasScreen
import ServiciosScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import co.fluxis.agenda.MenuItem
import co.fluxis.agenda.ScreenRoutes
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Observar la ruta actual para resaltar el ítem seleccionado
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val menuItems = listOf(
        MenuItem("Horarios", Icons.Default.Schedule, ScreenRoutes.Horarios.route),
        MenuItem("Clientes", Icons.Default.People, ScreenRoutes.Clientes.route),
        MenuItem("Reservas", Icons.Default.Event, ScreenRoutes.Reservas.route),
        MenuItem("Cierres", Icons.Default.Lock, ScreenRoutes.Cierres.route),
        MenuItem("Comisiones", Icons.Default.AttachMoney, ScreenRoutes.Comisiones.route),
        MenuItem("Servicios", Icons.Default.Build, ScreenRoutes.Servicios.route),
        MenuItem("Profesionales", Icons.Default.Badge, ScreenRoutes.Profesionales.route),
        MenuItem("Configuración", Icons.Default.Settings, ScreenRoutes.Settings.route)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Agenda",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )

                menuItems.forEach { item ->
                    val isSelected = currentRoute == item.route
                    NavigationDrawerItem(
                        label = { Text(item.title) },
                        icon = { Icon(item.icon, null) },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                // Evita acumular pantallas en el stack
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            // Cierra el sidebar
                            scope.launch { drawerState.close() }
                        },
                        // Personalización de colores
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Agenda") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, null)
                        }
                    }
                )
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = ScreenRoutes.Horarios.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(ScreenRoutes.Horarios.route) { HorariosScreen() }
                composable(ScreenRoutes.Clientes.route) { ClientesScreen() }
                composable(ScreenRoutes.Reservas.route) { ReservasScreen() }
                composable(ScreenRoutes.Cierres.route) { CierresScreen() }
                composable(ScreenRoutes.Comisiones.route) { ComisionesScreen() }
                composable(ScreenRoutes.Servicios.route) { ServiciosScreen() }
                composable(ScreenRoutes.Profesionales.route) { ProfesionalesScreen() }
            }
        }
    }
}
