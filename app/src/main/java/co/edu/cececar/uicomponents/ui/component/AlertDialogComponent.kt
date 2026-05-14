package co.edu.cececar.uicomponents.ui.component



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

// ─────────────────────────────────────────────
// 1. AlertDialog BÁSICO
//    Uso: confirmación simple con dos botones.
//
//    Ejemplo:
//      BasicAlertDialog(
//          title = "¿Cerrar sesión?",
//          message = "Tu sesión actual se cerrará.",
//          confirmText = "Cerrar sesión",
//          onConfirm = { /* acción */ },
//          onDismiss = { showDialog = false }
//      )
// ─────────────────────────────────────────────
@Composable
fun BasicAlertDialog(
    title: String,
    message: String,
    confirmText: String = "Aceptar",
    dismissText: String = "Cancelar",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        },
    )
}

// ─────────────────────────────────────────────
// 2. AlertDialog CON ÍCONO
//    Uso: información, permisos o advertencias
//    con contexto visual.
//
//    Ejemplo:
//      IconAlertDialog(
//          icon = Icons.Default.Info,
//          iconTint = MaterialTheme.colorScheme.primary,
//          title = "Permiso requerido",
//          message = "Necesitamos acceso a tu ubicación.",
//          confirmText = "Permitir",
//          onConfirm = { /* acción */ },
//          onDismiss = { showDialog = false }
//      )
// ─────────────────────────────────────────────
@Composable
fun IconAlertDialog(
    icon: ImageVector,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    title: String,
    message: String,
    confirmText: String = "Aceptar",
    dismissText: String = "Cancelar",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
            )
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        },
    )
}

// ─────────────────────────────────────────────
// 3. AlertDialog DESTRUCTIVO
//    Uso: eliminar, borrar datos o acciones
//    irreversibles. El botón de confirmación
//    usa el color de error del tema.
//
//    Ejemplo:
//      DestructiveAlertDialog(
//          title = "Eliminar cuenta",
//          message = "Esta acción es irreversible.",
//          confirmText = "Eliminar",
//          onConfirm = { /* eliminar */ },
//          onDismiss = { showDialog = false }
//      )
// ─────────────────────────────────────────────
@Composable
fun DestructiveAlertDialog(
    title: String,
    message: String,
    confirmText: String = "Eliminar",
    dismissText: String = "Cancelar",
    icon: ImageVector = Icons.Default.Delete,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
            )
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                ),
            ) {
                Text(confirmText, fontWeight = FontWeight.SemiBold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        },
    )
}

// ─────────────────────────────────────────────
// EJEMPLOS DE USO COMPLETO EN UN SCREEN
// ─────────────────────────────────────────────
//
// @Composable
// fun EjemploScreen() {
//     var showBasic by remember { mutableStateOf(false) }
//     var showIcon by remember { mutableStateOf(false) }
//     var showDestructive by remember { mutableStateOf(false) }
//
//     Column(
//         verticalArrangement = Arrangement.spacedBy(12.dp),
//         modifier = Modifier.padding(24.dp)
//     ) {
//         Button(onClick = { showBasic = true }) {
//             Text("Diálogo básico")
//         }
//         Button(onClick = { showIcon = true }) {
//             Text("Diálogo con ícono")
//         }
//         Button(onClick = { showDestructive = true }) {
//             Text("Diálogo destructivo")
//         }
//     }
//
//     if (showBasic) {
//         BasicAlertDialog(
//             title = "¿Cerrar sesión?",
//             message = "Tu sesión actual se cerrará.",
//             confirmText = "Cerrar sesión",
//             onConfirm = { /* acción */ },
//             onDismiss = { showBasic = false }
//         )
//     }
//
//     if (showIcon) {
//         IconAlertDialog(
//             icon = Icons.Default.Info,
//             title = "Permiso requerido",
//             message = "Necesitamos acceso a tu ubicación para continuar.",
//             confirmText = "Permitir",
//             onConfirm = { /* solicitar permiso */ },
//             onDismiss = { showIcon = false }
//         )
//     }
//
//     if (showDestructive) {
//         DestructiveAlertDialog(
//             title = "Eliminar cuenta",
//             message = "Esta acción es irreversible. Todos tus datos se eliminarán permanentemente.",
//             onConfirm = { /* eliminar */ },
//             onDismiss = { showDestructive = false }
//         )
//     }
// }