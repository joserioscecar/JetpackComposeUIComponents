package co.edu.cecar.nav.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import co.edu.cecar.nav.screens.DetailScreen
import co.edu.cecar.nav.screens.HomeScreen
import co.edu.cecar.nav.screens.ProfileScreen


@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(HomeRoute)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<HomeRoute> {
                HomeScreen(
                    onNavigateToDetail = { id, title ->
                        backStack.add(DetailRoute(itemId = id, title = title))
                    },
                    onNavigateToProfile = {
                        backStack.add(ProfileRoute)
                    }
                )
            }

            entry<DetailRoute> { route ->
                DetailScreen(
                    itemId = route.itemId,
                    title = route.title,
                    onBack = { backStack.removeLastOrNull() }
                )
            }

            entry<ProfileRoute> {
                ProfileScreen(
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}