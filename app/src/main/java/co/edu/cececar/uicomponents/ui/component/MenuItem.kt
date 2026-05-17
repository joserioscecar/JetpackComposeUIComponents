package co.edu.cececar.uicomponents.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────────────────────
// MODELO
// ─────────────────────────────────────────────────────────────────────────────

data class MenuItem(
    val id: String,
    val label: String,
    val icon: ImageVector,
    val badge: String? = null,      // texto opcional sobre el ícono (ej: "3")
    val enabled: Boolean = true,
)

// ─────────────────────────────────────────────────────────────────────────────
// LISTA COMPLETA
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Lista de opciones de menú con ícono, texto y flecha.
 * Similar a un ListView con items de navegación.
 *
 * Uso:
 * ```kotlin
 * val items = listOf(
 *     MenuItem("orders",   "Mis pedidos",    Icons.Outlined.ShoppingBag),
 *     MenuItem("benefits", "Mis Beneficios", Icons.Outlined.Star),
 *     MenuItem("coupons",  "Cupones",        Icons.Outlined.LocalOffer),
 * )
 *
 * MenuList(
 *     items = items,
 *     onItemClick = { item -> println(item.id) }
 * )
 * ```
 */
@Composable
fun MenuList(
    items: List<MenuItem>,
    onItemClick: (MenuItem) -> Unit,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
    ) {
        Column {
            items.forEachIndexed { index, item ->
                MenuListItem(
                    item = item,
                    onClick = { if (item.enabled) onItemClick(item) }
                )
                if (showDivider && index < items.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 56.dp),
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ITEM INDIVIDUAL
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun MenuListItem(
    item: MenuItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = item.enabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        // Ícono
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (item.enabled)
                MaterialTheme.colorScheme.onSurfaceVariant
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Label
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (item.enabled)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            modifier = Modifier.weight(1f)
        )

        // Badge opcional
        item.badge?.let {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        // Flecha
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = if (item.enabled) 1f else 0.38f
            )
        )
    }
}