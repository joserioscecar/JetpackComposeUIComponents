package co.edu.cececar.uicomponents.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.text.Normalizer




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AutocompleteField(
    items: List<T>,
    selectedItem: T? = null,
    onItemSelected: ((T) -> Unit)? = null,
    placeholder: String = "Buscar...",
    label: String? = null,                          // ← NUEVO
    itemLabel: (T) -> String = { if (it is ComponentItem) it.text else it.toString() },
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val filteredItems = remember(query) {
        if (query.isEmpty()) items
        else items.filter {
            itemLabel(it).withoutAccents().contains(query.withoutAccents(), ignoreCase = true)
        }
    }

    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = if (expanded) query else selectedItem?.let { itemLabel(it) } ?: "",
                onValueChange = {
                    query = it
                    expanded = true
                },
                label = label?.let { { Text(it) } },        // ← label DENTRO del campo
                placeholder = { Text(placeholder) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .fillMaxWidth().menuAnchor()
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
                            text = { Text(itemLabel(item)) },
                            onClick = {
                                onItemSelected?.invoke(item)
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


fun String.withoutAccents(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
}