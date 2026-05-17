package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.MenuItem
import co.edu.cececar.uicomponents.ui.component.MenuList

@Composable
fun MenuItemScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val menuItems = listOf(
            MenuItem("orders", "Mis pedidos", Icons.Outlined.ShoppingBag),
            MenuItem("exclusive", "Beneficios exclusivos", Icons.Outlined.CardGiftcard),
            MenuItem("benefits", "Mis Beneficios", Icons.Outlined.Star),
            MenuItem("coupons", "Cupones", Icons.Outlined.LocalOffer),
            MenuItem("address", "Dirección", Icons.Outlined.LocationOn),
            MenuItem("club", "Mi Club", Icons.Outlined.Home),
            MenuItem(
                "notifications",
                "Preferencias de notificaciones",
                Icons.Outlined.Notifications
            ),
            MenuItem("account", "Preferencias de cuenta", Icons.Outlined.Person),
        )

        MenuList(
            items = menuItems,
            onItemClick = { item ->
                println("Clicked: ${item.id}")
            }
        )
    }
}