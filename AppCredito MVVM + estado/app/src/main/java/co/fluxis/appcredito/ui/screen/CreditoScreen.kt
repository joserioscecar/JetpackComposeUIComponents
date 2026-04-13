package co.fluxis.appcredito.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.fluxis.appcredito.model.Credito
import co.fluxis.appcredito.ui.viewmodel.CreditoViewModel
import co.fluxis.appcredito.utils.format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditoScreen(
    viewModel: CreditoViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var monto by remember { mutableStateOf("") }
    var tasa by remember { mutableStateOf("") }
    var meses by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Crédito") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto del crédito") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = tasa,
                onValueChange = { tasa = it },
                label = { Text("Tasa anual (%)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = meses,
                onValueChange = { meses = it },
                label = { Text("Meses") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val m = monto.toDoubleOrNull() ?: 0.0
                    val t = tasa.toDoubleOrNull() ?: 0.0
                    val mes = meses.toIntOrNull() ?: 0

                    viewModel.calcularCredito(
                        Credito(
                            monto = m,
                            tasaAnual = t,
                            meses = mes
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Cuota mensual: ${uiState.cuota.format()}", style = MaterialTheme.typography.titleLarge)
            Text("Total pagado: ${uiState.totalPagado.format()}", style = MaterialTheme.typography.bodyLarge)
            Text("Intereses: ${uiState.intereses.format()}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
