package co.fluxis.agenda.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem(val title: String, val icon: ImageVector) {
    HORARIOS("Horarios", Icons.Default.Schedule),
    CLIENTES("Clientes", Icons.Default.People),
    RESERVAS("Reservas", Icons.Default.Event),
    CIERRES("Cierres", Icons.Default.Lock),
    COMISIONES("Comisiones", Icons.Default.AttachMoney),
    SERVICIOS("Servicios", Icons.Default.Build),
    PROFESIONALES("Profesionales", Icons.Default.Person)
}
