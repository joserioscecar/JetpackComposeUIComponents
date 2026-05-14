package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.BasicAlertDialog
import co.edu.cececar.uicomponents.ui.component.DestructiveAlertDialog
import co.edu.cececar.uicomponents.ui.component.IconAlertDialog

@Composable
fun  AlertScreen(){


    // Estados para controlar qué diálogo está visible
    var showBasic       by remember { mutableStateOf(false) }
    var showIcon        by remember { mutableStateOf(false) }
    var showDestructive by remember { mutableStateOf(false) }

    // ── UI principal ──────────────────────────
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Ejemplos de AlertDialog",
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botón 1 → diálogo básico
        Button(
            onClick = { showBasic = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Abrir diálogo básico")
        }

        // Botón 2 → diálogo con ícono
        Button(
            onClick = { showIcon = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Abrir diálogo con ícono")
        }

        // Botón 3 → diálogo destructivo
        Button(
            onClick = { showDestructive = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
            ),
        ) {
            Text("Abrir diálogo destructivo")
        }
    }

    // ── Diálogos (se renderizan solo si su estado es true) ──

    if (showBasic) {
        BasicAlertDialog(
            title = "¿Cerrar sesión?",
            message = "Tu sesión actual se cerrará. Tendrás que iniciar sesión nuevamente.",
            confirmText = "Cerrar sesión",
            onConfirm = {
                // Aquí va tu lógica: cerrar sesión, navegar, etc.
            },
            onDismiss = { showBasic = false },
        )
    }

    if (showIcon) {
        IconAlertDialog(
            icon = Icons.Default.Info,
            iconTint = MaterialTheme.colorScheme.primary,
            title = "Permiso requerido",
            message = "Esta función necesita acceso a tu ubicación para funcionar correctamente.",
            confirmText = "Permitir",
            onConfirm = {
                // Aquí va tu lógica: solicitar permiso, etc.
            },
            onDismiss = { showIcon = false },
        )
    }

    if (showDestructive) {
        DestructiveAlertDialog(
            title = "Eliminar cuenta",
            message = "Esta acción es irreversible. Todos tus datos serán eliminados permanentemente.",
            confirmText = "Eliminar",
            onConfirm = {
                // Aquí va tu lógica: llamar al repositorio, navegar, etc.
            },
            onDismiss = { showDestructive = false },
        )
    }
}