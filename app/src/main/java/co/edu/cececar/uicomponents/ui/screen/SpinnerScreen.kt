package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.Spinner
import co.edu.cececar.uicomponents.ui.component.SpinnerConfig
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SpinnerScreen() {

    var cantidad by remember { mutableStateOf(1) }
    val precioPorUnidad = 15000

    val total = cantidad * precioPorUnidad

    val formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"))

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Resumen de compra",
            style = MaterialTheme.typography.titleLarge
        )

        Spinner(
            value = cantidad,
            onValueChange = { cantidad = it },
            config = SpinnerConfig(
                label = "Cantidad",
                minValue = 1,
                maxValue = 99,
                suffix = "und"
            )
        )

        HorizontalDivider()

        // Desglose
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Precio por unidad", style = MaterialTheme.typography.bodyMedium)
            Text(formatter.format(precioPorUnidad), style = MaterialTheme.typography.bodyMedium)
        }

        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Cantidad", style = MaterialTheme.typography.bodyMedium)
            Text("$cantidad und", style = MaterialTheme.typography.bodyMedium)
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", style = MaterialTheme.typography.titleMedium)
            Text(
                text = formatter.format(total),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }


    }
}