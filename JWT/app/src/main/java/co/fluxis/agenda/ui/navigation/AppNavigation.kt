package co.fluxis.agenda.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import co.fluxis.agenda.data.local.SessionManager
import co.fluxis.agenda.ui.screens.ClientesScreen
import co.fluxis.agenda.ui.screens.LoginScreen

@Composable
fun AppNavigation() {
    val backStack = remember {
        val initialScreen = if (SessionManager.token != null) Screen.Clientes else Screen.Login
        mutableStateListOf(initialScreen)
    }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.size - 1)
            }
        }
    ) { screen ->
        when (screen) {
            Screen.Login -> NavEntry(screen) {
                LoginScreen(
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(Screen.Clientes)
                    }
                )
            }

            Screen.Clientes -> NavEntry(screen) {
                ClientesScreen(
                    onLogout = {
                        SessionManager.token = null
                        backStack.clear()
                        backStack.add(Screen.Login)
                    }
                )
            }
        }
    }
}
