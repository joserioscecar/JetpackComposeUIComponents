package co.edu.cececar.uicomponents.ui.component


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*


data class DatePickerConfig @OptIn(ExperimentalMaterial3Api::class) constructor(
    /** Etiqueta del campo de texto */
    val label: String = "Fecha",
    /** Texto cuando no hay fecha seleccionada */
    val placeholder: String = "Selecciona una fecha",
    /** Formato de visualización de la fecha (SimpleDateFormat) */
    val dateFormat: String = "dd/MM/yyyy",
    /** Título del diálogo */
    val dialogTitle: String = "Seleccionar fecha",
    /** Texto del botón de confirmación */
    val confirmLabel: String = "Aceptar",
    /** Texto del botón de cancelación */
    val dismissLabel: String = "Cancelar",
    /** Permitir limpiar la fecha seleccionada */
    val clearable: Boolean = true,
    /** Deshabilitar el campo completo */
    val enabled: Boolean = true,
    /** Mostrar mensaje de error */
    val errorMessage: String? = null,
    /** Texto de apoyo debajo del campo */
    val supportingText: String? = null,
    /** Fechas seleccionables (null = todas) */
    val selectableDates: SelectableDates? = null,
    /** Milisegundos de fecha mínima seleccionable */
    val minDateMillis: Long? = null,
    /** Milisegundos de fecha máxima seleccionable */
    val maxDateMillis: Long? = null,
    /** Rango de años navegables */
    val yearRange: IntRange = DatePickerDefaults.YearRange,
    /** Modo inicial: calendario (Picker) o entrada de texto (Input) */
    val initialDisplayMode: DisplayMode = DisplayMode.Picker,
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    selectedDateMillis: Long?,
    onDateSelected: (Long?) -> Unit,
    modifier: Modifier = Modifier,
    config: DatePickerConfig = DatePickerConfig(),
) {
    var showDialog by remember { mutableStateOf(false) }

    val dateFormatter = remember(config.dateFormat) {
        SimpleDateFormat(config.dateFormat, Locale.getDefault())
    }

    val displayText = remember(selectedDateMillis, config.dateFormat) {
        selectedDateMillis?.let { dateFormatter.format(Date(it)) }
    }
    OutlinedTextField(
        value = displayText ?: "",
        onValueChange = {},
        readOnly = true,
        enabled = config.enabled,
        modifier = modifier.fillMaxWidth(),
        label = { Text(config.label) },
        placeholder = { Text(config.placeholder) },
        trailingIcon = {
            if (config.clearable && selectedDateMillis != null) {
                IconButton(onClick = { onDateSelected(null) }) {
                    Icon(Icons.Outlined.Clear, contentDescription = "Limpiar fecha")
                }
            } else {
                IconButton(
                    onClick = { if (config.enabled) showDialog = true },
                    enabled = config.enabled,
                ) {
                    Icon(Icons.Outlined.CalendarToday, contentDescription = "Abrir calendario")
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

        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
            .also { source ->
                LaunchedEffect(source) {
                    source.interactions.collect {
                        if (it is androidx.compose.foundation.interaction.PressInteraction.Release) {
                            if (config.enabled) showDialog = true
                        }
                    }
                }
            },
    )

    if (showDialog) {
        DatePickerFieldDialog(
            initialDateMillis = selectedDateMillis,
            config = config,
            onDismiss = { showDialog = false },
            onConfirm = { millis ->
                onDateSelected(millis)
                showDialog = false
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerFieldDialog(
    initialDateMillis: Long?,
    config: DatePickerConfig,
    onDismiss: () -> Unit,
    onConfirm: (Long?) -> Unit,
) {
    // Construye SelectableDates a partir de config
    val selectableDates = remember(config.minDateMillis, config.maxDateMillis, config.selectableDates) {
        when {
            config.selectableDates != null -> config.selectableDates
            config.minDateMillis != null || config.maxDateMillis != null -> object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val afterMin = config.minDateMillis?.let { utcTimeMillis >= it } ?: true
                    val beforeMax = config.maxDateMillis?.let { utcTimeMillis <= it } ?: true
                    return afterMin && beforeMax
                }
            }
            else -> DatePickerDefaults.AllDates
        }
    }

    val pickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDateMillis,
        yearRange = config.yearRange,
        initialDisplayMode = config.initialDisplayMode,
        selectableDates = selectableDates,
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(pickerState.selectedDateMillis) }) {
                Text(config.confirmLabel)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(config.dismissLabel)
            }
        },
    ) {
        DatePicker(
            state = pickerState,
            title = { Text(config.dialogTitle, modifier = Modifier.padding(start = 24.dp, top = 24.dp)) },
            showModeToggle = true,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(
    startDateMillis: Long?,
    endDateMillis: Long?,
    onRangeSelected: (start: Long?, end: Long?) -> Unit,
    modifier: Modifier = Modifier,
    startLabel: String = "Fecha inicio",
    endLabel: String = "Fecha fin",
    dateFormat: String = "dd/MM/yyyy",
    dialogTitle: String = "Seleccionar período",
    enabled: Boolean = true,
) {
    var showDialog by remember { mutableStateOf(false) }

    val formatter = remember(dateFormat) { SimpleDateFormat(dateFormat, Locale.getDefault()) }
    fun Long?.fmt() = this?.let { formatter.format(Date(it)) } ?: ""

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = startDateMillis.fmt(),
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            label = { Text(startLabel) },
            trailingIcon = {
                IconButton(onClick = { if (enabled) showDialog = true }) {
                    Icon(Icons.Outlined.CalendarToday, contentDescription = null)
                }
            },
            singleLine = true,
            modifier = Modifier.weight(1f),
        )
        OutlinedTextField(
            value = endDateMillis.fmt(),
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            label = { Text(endLabel) },
            trailingIcon = {
                IconButton(onClick = { if (enabled) showDialog = true }) {
                    Icon(Icons.Outlined.CalendarToday, contentDescription = null)
                }
            },
            singleLine = true,
            modifier = Modifier.weight(1f),
        )
    }

    if (showDialog) {
        val rangeState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = startDateMillis,
            initialSelectedEndDateMillis = endDateMillis,
        )
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRangeSelected(rangeState.selectedStartDateMillis, rangeState.selectedEndDateMillis)
                        showDialog = false
                    },
                    enabled = rangeState.selectedEndDateMillis != null,
                ) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
            },
        ) {
            DateRangePicker(
                state = rangeState,
                title = { Text(dialogTitle, modifier = Modifier.padding(start = 24.dp, top = 24.dp)) },
                modifier = Modifier.height(500.dp),
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// EXTENSIONES DE UTILIDAD
// ─────────────────────────────────────────────────────────────────────────────

/** Convierte milisegundos UTC a String con el formato indicado. */
fun Long.toFormattedDate(format: String = "dd/MM/yyyy"): String =
    SimpleDateFormat(format, Locale.getDefault()).format(Date(this))

/** Convierte milisegundos UTC a un objeto [Date]. */
fun Long.toDate(): Date = Date(this)

/** Retorna true si la fecha está entre [min] y [max] (inclusive). */
fun Long.isInRange(min: Long?, max: Long?): Boolean {
    val afterMin = min?.let { this >= it } ?: true
    val beforeMax = max?.let { this <= it } ?: true
    return afterMin && beforeMax
}

/** Inicio del día (00:00:00) en milisegundos para el Long dado. */
fun Long.startOfDay(): Long {
    val cal = Calendar.getInstance().apply { timeInMillis = this@startOfDay }
    cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
    return cal.timeInMillis
}

/** Fin del día (23:59:59) en milisegundos para el Long dado. */
fun Long.endOfDay(): Long {
    val cal = Calendar.getInstance().apply { timeInMillis = this@endOfDay }
    cal.set(Calendar.HOUR_OF_DAY, 23); cal.set(Calendar.MINUTE, 59)
    cal.set(Calendar.SECOND, 59); cal.set(Calendar.MILLISECOND, 999)
    return cal.timeInMillis
}