package co.edu.cececar.uicomponents.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp


@Composable
fun <T> RadioButtonGroup(
    items: List<T>,
    selectedItem: T? = null,
    onItemSelected: ((T) -> Unit)? = null,
    itemLabel: (T) -> String = { if (it is ComponentItem) it.text else it.toString() },
    itemKey: (T) -> String = { if (it is ComponentItem) it.id else it.toString() },
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
                        selected = itemKey(item) == selectedItem?.let { itemKey(it) },
                        onClick = { onItemSelected?.invoke(item) },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = itemKey(item) == selectedItem?.let { itemKey(it) },
                    onClick = null
                )
                Text(
                    text = itemLabel(item),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}