package co.edu.cececar.uicomponents.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.reflect.jvm.isAccessible


@Composable
fun <T : Any> KeyValueList(
    item: T,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    exclude: List<String> = emptyList(),
    labels: Map<String, String> = emptyMap(),
) {
    val items = remember(item) {
        item::class.constructors.first().parameters
            .mapNotNull { param ->
                item::class.members
                    .filterIsInstance<kotlin.reflect.KProperty1<T, *>>()
                    .find { it.name == param.name }
            }
            .filter { it.name !in exclude }
            .map { prop ->
                prop.isAccessible = true
                val label = labels[prop.name] ?: prop.name.replaceFirstChar { it.uppercase() }
                val value = prop.get(item)?.toString() ?: "-"
                label to value
            }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        items.forEachIndexed { index, (key, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(key,   style = MaterialTheme.typography.bodyMedium)
                Text(value, style = MaterialTheme.typography.bodyMedium)
            }
            if (showDivider && index < items.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}