package co.edu.cececar.uicomponents.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────────────────────
// CONFIGURACIÓN
// ─────────────────────────────────────────────────────────────────────────────

data class SearchFieldConfig(
    val label: String = "Buscar",
    val placeholder: String = "Escribe para buscar...",
    val clearable: Boolean = true,
    val enabled: Boolean = true,
    val searchOnKeyboard: Boolean = true,   // busca al presionar Enter del teclado
    val errorMessage: String? = null,
)

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    config: SearchFieldConfig = SearchFieldConfig(),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(config.placeholder) },
        trailingIcon = {
            if (config.clearable && value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(Icons.Outlined.Clear, contentDescription = "Limpiar")
                }
            } else {
                IconButton(
                    onClick = { onSearch(value) },
                    enabled = config.enabled,
                ) {
                    Icon(Icons.Outlined.Search, contentDescription = "Buscar")
                }
            }
        },
        isError = config.errorMessage != null,
        supportingText = config.errorMessage?.let { { Text(it) } },
        enabled = config.enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = if (config.searchOnKeyboard) ImeAction.Search else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onSearch = { if (config.searchOnKeyboard) onSearch(value) }
        ),
        modifier = modifier.fillMaxWidth(),
    )
}