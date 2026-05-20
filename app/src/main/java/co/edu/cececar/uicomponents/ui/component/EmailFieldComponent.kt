package co.edu.cececar.uicomponents.ui.component



import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

// ─────────────────────────────────────────────────────────────────────────────
// CONFIGURACIÓN
// ─────────────────────────────────────────────────────────────────────────────

data class EmailFieldConfig(
    val label: String = "Correo electrónico",
    val placeholder: String = "ejemplo@correo.com",
    val clearable: Boolean = true,
    val enabled: Boolean = true,
    val errorMessage: String? = null,
    val supportingText: String? = null,
    val imeAction: ImeAction = ImeAction.Done,
    val validateOnChange: Boolean = true,
)

// ─────────────────────────────────────────────────────────────────────────────
// VALIDACIÓN
// ─────────────────────────────────────────────────────────────────────────────

private val emailRegex = Regex(
    "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$"
)

fun String.isValidEmail(): Boolean = emailRegex.matches(this.trim())

// ─────────────────────────────────────────────────────────────────────────────
// COMPONENTE
// ─────────────────────────────────────────────────────────────────────────────


@Composable
fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    config: EmailFieldConfig = EmailFieldConfig(),
) {
    var touched by remember { mutableStateOf(false) }

    val validationError = when {
        config.errorMessage != null -> config.errorMessage
        config.validateOnChange && touched && value.isNotEmpty() && !value.isValidEmail() ->
            "Correo electrónico inválido"
        config.validateOnChange && touched && value.isEmpty() ->
            "El correo es obligatorio"
        else -> null
    }

    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            touched = true
            onValueChange(input.trim())
        },
        label = { Text(config.label) },
        placeholder = { Text(config.placeholder) },
        leadingIcon = {
            Icon(Icons.Outlined.Email, contentDescription = null)
        },
        trailingIcon = {
            if (config.clearable && value.isNotEmpty()) {
                IconButton(onClick = {
                    onValueChange("")
                    touched = false
                }) {
                    Icon(Icons.Outlined.Clear, contentDescription = "Limpiar")
                }
            }
        },
        isError = validationError != null,
        supportingText = when {
            validationError != null -> ({ Text(validationError) })
            config.supportingText != null -> ({ Text(config.supportingText) })
            else -> null
        },
        enabled = config.enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            imeAction = config.imeAction,
        ),
        modifier = modifier.fillMaxWidth(),
    )



}