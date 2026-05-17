package co.edu.cececar.uicomponents.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────────────────────
// CONFIGURACIÓN
// ─────────────────────────────────────────────────────────────────────────────

data class SpinnerConfig(
    val label: String = "",
    val minValue: Int = 0,
    val maxValue: Int = 100,
    val step: Int = 1,
    val suffix: String = "",
)

// ─────────────────────────────────────────────────────────────────────────────
// SpinnerField — numérico
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    config: SpinnerConfig = SpinnerConfig(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val displayText = if (config.suffix.isNotEmpty()) "$value ${config.suffix}" else "$value"

    BasicTextField(
        value = displayText,
        onValueChange = {},
        readOnly = true,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            textAlign = TextAlign.Center,
        ),
        interactionSource = interactionSource,
        modifier = modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = displayText,
                innerTextField = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                if (value - config.step >= config.minValue)
                                    onValueChange(value - config.step)
                            },
                            enabled = value > config.minValue,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Remove,
                                contentDescription = "Disminuir",
                                tint = if (value > config.minValue)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            )
                        }

                        innerTextField()

                        IconButton(
                            onClick = {
                                if (value + config.step <= config.maxValue)
                                    onValueChange(value + config.step)
                            },
                            enabled = value < config.maxValue,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Add,
                                contentDescription = "Aumentar",
                                tint = if (value < config.maxValue)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            )
                        }
                    }
                },
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                label = if (config.label.isNotEmpty()) {
                    { Text(config.label) }
                } else null,
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = true,
                        isError = false,
                        interactionSource = interactionSource,
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Spinner(
    items: List<T>,
    selectedIndex: Int,
    onIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    config: SpinnerConfig = SpinnerConfig(),
    itemLabel: (T) -> String = { it.toString() },
) {
    val interactionSource = remember { MutableInteractionSource() }
    val displayText = items.getOrNull(selectedIndex)?.let { itemLabel(it) } ?: ""

    BasicTextField(
        value = displayText,
        onValueChange = {},
        readOnly = true,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            textAlign = TextAlign.Center,
        ),
        interactionSource = interactionSource,
        modifier = modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = displayText,
                innerTextField = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = { onIndexChange(selectedIndex - 1) },
                            enabled = selectedIndex > 0,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Remove,
                                contentDescription = "Anterior",
                                tint = if (selectedIndex > 0)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            )
                        }

                        innerTextField()

                        IconButton(
                            onClick = { onIndexChange(selectedIndex + 1) },
                            enabled = selectedIndex < items.lastIndex,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Add,
                                contentDescription = "Siguiente",
                                tint = if (selectedIndex < items.lastIndex)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            )
                        }
                    }
                },
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                label = if (config.label.isNotEmpty()) {
                    { Text(config.label) }
                } else null,
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = true,
                        isError = false,
                        interactionSource = interactionSource,
                    )
                }
            )
        }
    )
}