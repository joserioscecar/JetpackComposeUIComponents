package co.edu.cececar.uicomponents.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────────────────────
// MODELO
// ─────────────────────────────────────────────────────────────────────────────

data class TimeValue(
    val hour: Int,
    val minute: Int,
) {
    /** Retorna la hora formateada. Ej: "08:30" o "8:30 AM" */
    fun format(use24Hour: Boolean = true): String {
        return if (use24Hour) {
            "%02d:%02d".format(hour, minute)
        } else {
            val displayHour = when {
                hour == 0 -> 12
                hour > 12 -> hour - 12
                else -> hour
            }
            val period = if (hour < 12) "AM" else "PM"
            "%d:%02d %s".format(displayHour, minute, period)
        }
    }
}

data class TimePickerConfig(
    val label: String = "Hora",
    val confirmLabel: String = "Aceptar",
    val dismissLabel: String = "Cancelar",
    val clearable: Boolean = true,
    val enabled: Boolean = true,
    val use24Hour: Boolean = true,
    val errorMessage: String? = null,
    val supportingText: String? = null,
)

// ─────────────────────────────────────────────────────────────────────────────
// COMPONENTE PRINCIPAL
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Campo de hora reutilizable con diálogo TimePicker de Material 3.
 *
 * Uso básico:
 * ```kotlin
 * var hora by remember { mutableStateOf<TimeValue?>(null) }
 * TimePickerField(
 *     selectedTime = hora,
 *     onTimeSelected = { hora = it }
 * )
 * ```
 *
 * Con configuración:
 * ```kotlin
 * TimePickerField(
 *     selectedTime = hora,
 *     onTimeSelected = { hora = it },
 *     config = TimePickerConfig(
 *         label = "Hora de entrega",
 *         use24Hour = false,
 *         clearable = false
 *     )
 * )
 * ```
 *
 * Leer el valor:
 * ```kotlin
 * hora?.hour      // 14
 * hora?.minute    // 30
 * hora?.format()  // "14:30"
 * hora?.format(use24Hour = false) // "2:30 PM"
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    selectedTime: TimeValue?,
    onTimeSelected: (TimeValue?) -> Unit,
    modifier: Modifier = Modifier,
    config: TimePickerConfig = TimePickerConfig(),
) {
    var showDialog by remember { mutableStateOf(false) }

    val displayText = selectedTime?.format(config.use24Hour) ?: ""

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect {
            if (it is PressInteraction.Release && config.enabled) {
                showDialog = true
            }
        }
    }

    OutlinedTextField(
        value = displayText,
        onValueChange = {},
        readOnly = true,
        enabled = config.enabled,
        modifier = modifier.fillMaxWidth(),
        label = { Text(config.label) },
        placeholder = { Text(if (config.use24Hour) "00:00" else "12:00 AM") },
        trailingIcon = {
            if (config.clearable && selectedTime != null) {
                IconButton(onClick = { onTimeSelected(null) }) {
                    Icon(Icons.Outlined.Clear, contentDescription = "Limpiar hora")
                }
            } else {
                IconButton(
                    onClick = { if (config.enabled) showDialog = true },
                    enabled = config.enabled,
                ) {
                    Icon(Icons.Outlined.AccessTime, contentDescription = "Seleccionar hora")
                }
            }
        },
        supportingText = when {
            config.errorMessage != null -> ({ Text(config.errorMessage) })
            config.supportingText != null -> ({ Text(config.supportingText) })
            else -> null
        },
        isError = config.errorMessage != null,
        singleLine = true,
        interactionSource = interactionSource,
    )

    if (showDialog) {
        TimePickerDialog(
            initialTime = selectedTime,
            config = config,
            onDismiss = { showDialog = false },
            onConfirm = { time ->
                onTimeSelected(time)
                showDialog = false
            }
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// DIÁLOGO INTERNO
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    initialTime: TimeValue?,
    config: TimePickerConfig,
    onDismiss: () -> Unit,
    onConfirm: (TimeValue) -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime?.hour ?: 0,
        initialMinute = initialTime?.minute ?: 0,
        is24Hour = config.use24Hour,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(config.label) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TimePicker(
                    state = timePickerState,
                    layoutType = TimePickerLayoutType.Vertical,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = MaterialTheme.colorScheme.surfaceVariant,
                        selectorColor = MaterialTheme.colorScheme.primary,
                    )
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    TimeValue(
                        hour = timePickerState.hour,
                        minute = timePickerState.minute,
                    )
                )
            }) {
                Text(config.confirmLabel)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(config.dismissLabel)
            }
        }
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// VARIANTE: RANGO DE HORAS
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Dos campos de hora (inicio y fin) para seleccionar un rango.
 *
 * Uso:
 * ```kotlin
 * var horaInicio by remember { mutableStateOf<TimeValue?>(null) }
 * var horaFin    by remember { mutableStateOf<TimeValue?>(null) }
 *
 * TimeRangePickerField(
 *     startTime = horaInicio,
 *     endTime   = horaFin,
 *     onStartSelected = { horaInicio = it },
 *     onEndSelected   = { horaFin = it },
 * )
 * ```
 */
@Composable
fun TimeRangePicker(
    startTime: TimeValue?,
    endTime: TimeValue?,
    onStartSelected: (TimeValue?) -> Unit,
    onEndSelected: (TimeValue?) -> Unit,
    modifier: Modifier = Modifier,
    startLabel: String = "Hora inicio",
    endLabel: String = "Hora fin",
    use24Hour: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TimePicker(
            selectedTime = startTime,
            onTimeSelected = onStartSelected,
            modifier = Modifier.weight(1f),
            config = TimePickerConfig(label = startLabel, use24Hour = use24Hour),
        )
        TimePicker(
            selectedTime = endTime,
            onTimeSelected = onEndSelected,
            modifier = Modifier.weight(1f),
            config = TimePickerConfig(label = endLabel, use24Hour = use24Hour),
        )
    }
}