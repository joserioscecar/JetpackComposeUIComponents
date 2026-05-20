package co.edu.cececar.uicomponents.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp


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

data class CheckboxItem(
    val id: String,
    val text: String,
    val checked: Boolean = false
)