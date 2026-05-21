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
fun <T : Any> DataList(
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


@Composable
fun <T : Any> DataList(
    items: List<T>,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    exclude: List<String> = emptyList(),
    labels: Map<String, String> = emptyMap(),
) {
    Column(modifier = modifier.fillMaxWidth()) {
        items.forEachIndexed { index, item ->
            // Encabezado con el índice
            Text(
                text = "#${index + 1}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            // Propiedades del objeto
            val props = remember(item) {
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

            props.forEachIndexed { pIndex, (key, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(key,   style = MaterialTheme.typography.bodyMedium)
                    Text(value, style = MaterialTheme.typography.bodyMedium)
                }
                if (showDivider && pIndex < props.lastIndex) {
                    HorizontalDivider()
                }
            }

            // Separador entre objetos
            if (showDivider && index < items.lastIndex) {
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}