package co.edu.cececar.uicomponents.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

// ─────────────────────────────────────────────────────────────────────────────
// TIPO DE NÚMERO
// ─────────────────────────────────────────────────────────────────────────────

sealed class NumberType {
    object Integer : NumberType()
    data class Decimal(val decimals: Int = 2) : NumberType()
}

// ─────────────────────────────────────────────────────────────────────────────
// CONFIGURACIÓN
// ─────────────────────────────────────────────────────────────────────────────

data class NumberFieldConfig(
    val label: String = "",
    val placeholder: String = "0",
    val numberType: NumberType = NumberType.Integer,
    val minValue: Double? = null,
    val maxValue: Double? = null,
    val suffix: String = "",
    val prefix: String = "",
    val errorMessage: String? = null,
    val supportingText: String? = null,
    val enabled: Boolean = true,
    val locale: Locale = Locale("es", "CO"),
)

@Composable
fun NumberField(
    value: Number?,
    onValueChange: (Number?) -> Unit,
    modifier: Modifier = Modifier,
    config: NumberFieldConfig = NumberFieldConfig(),
) {
    val symbols = DecimalFormatSymbols(config.locale)
    val decimalSeparator = symbols.decimalSeparator
    val groupingSeparator = symbols.groupingSeparator

    // Formato para mostrar con separador de miles
    val displayFormatter = remember(config) {
        when (val type = config.numberType) {
            is NumberType.Integer -> DecimalFormat("#,##0", symbols)
            is NumberType.Decimal -> {
                val pattern = "#,##0.${"0".repeat(type.decimals)}"
                DecimalFormat(pattern, symbols)
            }
        }
    }

    var rawInput by remember { mutableStateOf(value?.let { displayFormatter.format(it) } ?: "") }
    var validationError by remember { mutableStateOf<String?>(null) }

    // Determina el error a mostrar (externo o de validación interna)
    val errorToShow = config.errorMessage ?: validationError

    OutlinedTextField(
        value = rawInput,
        onValueChange = { input ->
            // Limpia todo excepto dígitos y separador decimal
            val onlyDigits = if (config.numberType is NumberType.Decimal)
                input.replace(Regex("[^0-9$decimalSeparator]"), "")
            else
                input.replace(Regex("[^0-9]"), "")

            // Separa parte entera y decimal
            val parts = onlyDigits.split(decimalSeparator)
            val intPart = parts.getOrElse(0) { "" }
            val decPart = parts.getOrElse(1) { null }

            // Formatea la parte entera con separadores de miles
            val formattedInt = if (intPart.isEmpty()) ""
            else DecimalFormat("#,##0", symbols).format(intPart.toLongOrNull() ?: 0L)

            // Reconstruye el valor mostrado
            rawInput = when {
                decPart != null -> "$formattedInt$decimalSeparator$decPart"  // mantiene el decimal mientras escribe
                onlyDigits.endsWith(decimalSeparator) -> "$formattedInt$decimalSeparator"
                else -> formattedInt
            }

            // Parsear para el callback
            val parsed: Number? = when (config.numberType) {
                is NumberType.Integer -> intPart.toLongOrNull()?.toInt()
                is NumberType.Decimal -> onlyDigits.replace(decimalSeparator, '.').toDoubleOrNull()
            }

            // Validar min/max
            validationError = when {
                parsed == null && onlyDigits.isNotEmpty() -> "Número inválido"
                parsed != null && config.minValue != null && parsed.toDouble() < config.minValue ->
                    "Valor mínimo: ${displayFormatter.format(config.minValue)}"
                parsed != null && config.maxValue != null && parsed.toDouble() > config.maxValue ->
                    "Valor máximo: ${displayFormatter.format(config.maxValue)}"
                else -> null
            }

            onValueChange(parsed)
        },
        label = if (config.label.isNotEmpty()) ({ Text(config.label) }) else null,
        placeholder = { Text(config.placeholder) },
        prefix = if (config.prefix.isNotEmpty()) ({ Text(config.prefix) }) else null,
        suffix = if (config.suffix.isNotEmpty()) ({ Text(config.suffix) }) else null,
        isError = errorToShow != null,
        supportingText = when {
            errorToShow != null -> ({ Text(errorToShow) })
            config.supportingText != null -> ({ Text(config.supportingText) })
            else -> null
        },
        enabled = config.enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = when (config.numberType) {
                is NumberType.Integer -> KeyboardType.Number
                is NumberType.Decimal -> KeyboardType.Decimal
            }
        ),
        modifier = modifier.fillMaxWidth(),
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// EXTENSIONES DE UTILIDAD
// ─────────────────────────────────────────────────────────────────────────────

/** Formatea un número con separador de miles en locale es-CO */
fun Number.formatThousands(locale: Locale = Locale("es", "CO")): String {
    val symbols = DecimalFormatSymbols(locale)
    return when (this) {
        is Int, is Long -> DecimalFormat("#,##0", symbols).format(this)
        else -> DecimalFormat("#,##0.##", symbols).format(this)
    }
}