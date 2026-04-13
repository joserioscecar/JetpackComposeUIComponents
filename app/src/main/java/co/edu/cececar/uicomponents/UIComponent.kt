package co.edu.cececar.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

import java.text.Normalizer

fun String.sinTildes(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
}

interface Item{
    val id: String
    val text: String
}


data class CheckboxItem(
    override val id: String,
    override val text: String,
    val checked: Boolean = false
): Item


data class ComponentItem(
    override val id: String,
    override val text: String
): Item


@Composable
fun CheckboxGroup(
    items: List<CheckboxItem>,
    onItemToggled: (CheckboxItem) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        label?.invoke()
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = item.checked,
                        onValueChange = { onItemToggled(item) },
                        role = Role.Checkbox
                    )
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = item.checked,
                    onCheckedChange = null
                )
                Text(
                    text = item.text,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}




// DropdownField.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    items: List<ComponentItem>,
    selectedItem: ComponentItem?,
    onItemSelected: (ComponentItem) -> Unit,
    placeholder: String = "Selecciona una opcion",
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = if (selectedItem?.id.isNullOrEmpty()) "" else selectedItem.text,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text(placeholder) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(placeholder, color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    onClick = {
                        onItemSelected(ComponentItem("", placeholder))
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.text) },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
fun RadioButtonGroup(
    items: List<ComponentItem>,
    selectedId: String?,
    onItemSelected: (ComponentItem) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        label?.invoke()
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = item.id == selectedId,
                        onClick = { onItemSelected(item) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = item.id == selectedId,
                    onClick = null
                )
                Text(
                    text = item.text,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableDropdownField(
    items: List<ComponentItem>,
    selectedItem: ComponentItem?,
    onItemSelected: (ComponentItem) -> Unit,
    placeholder: String = "Buscar...",
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val filteredItems = remember(query) {
        if (query.isEmpty()) items
        else items.filter {
            it.text.sinTildes()
                .contains(query.sinTildes(), ignoreCase = true)
        }
    }

    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = if (expanded) query else selectedItem?.text ?: "",
                onValueChange = {
                    query = it
                    expanded = true
                },
                placeholder = { Text(placeholder) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    query = ""
                }
            ) {
                if (filteredItems.isEmpty()) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Sin resultados",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        onClick = {},
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                } else {
                    filteredItems.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item.text) },
                            onClick = {
                                onItemSelected(item)
                                expanded = false
                                query = ""
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }
    }
}


