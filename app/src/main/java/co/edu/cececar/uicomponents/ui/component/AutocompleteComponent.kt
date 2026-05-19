package co.edu.cececar.uicomponents.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.Normalizer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Autocomplete(
    items: List<T>,
    selectedItem: T? = null,
    onItemSelected: ((T) -> Unit)? = null,
    placeholder: String = "Buscar...",
    label: String? = null,
    itemLabel: (T) -> String = { if (it is ComponentItem) it.text else it.toString() },
    modifier: Modifier = Modifier,
) {
    var query        by remember { mutableStateOf("") }
    var expanded     by remember { mutableStateOf(false) }
    var isFocused    by remember { mutableStateOf(false) }
    var justSelected by remember { mutableStateOf(false) }

    val filteredItems = remember(query, items) {
        if (query.isBlank()) items
        else {
            val words = query.withoutAccents().trim().split(" ").filter { it.isNotEmpty() }
            items.filter { item ->
                val text = itemLabel(item).withoutAccents()
                words.all { word -> text.contains(word, ignoreCase = true) }
            }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = if (isFocused && !justSelected) query else selectedItem?.let { itemLabel(it) } ?: "",
            onValueChange = { input ->
                query = input
                expanded = input.isNotEmpty()
            },
            label = label?.let { { Text(it) } },
            placeholder = { Text(placeholder) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = OutlinedTextFieldDefaults.colors(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focus ->
                    if (justSelected) {
                        justSelected = false
                        return@onFocusChanged
                    }
                    isFocused = focus.isFocused
                    if (!focus.isFocused) {
                        expanded = false
                        query = ""
                    }
                }
        )

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 240.dp)
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(8.dp),
                        ambientColor = Color.Gray.copy(alpha = 0.15f),
                        spotColor = Color.Black.copy(alpha = 0.10f)
                    )
                    .border(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.small
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    )
                    .verticalScroll(rememberScrollState())
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
                                justSelected = true
                                onItemSelected?.invoke(item)
                                expanded = false
                                query = ""
                                isFocused = false
                            },
                        //    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
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