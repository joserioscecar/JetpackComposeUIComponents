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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.fluxis.appcredito.ui.viewmodel.CreditoViewModel
import co.fluxis.appcredito.utils.format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditoScreen(
    viewModel: CreditoViewModel = viewModel()
) {
    val uiState = viewModel.uiState

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
                value = uiState.monto,
                onValueChange = { viewModel.onMontoChanged(it) },
                label = { Text("Monto del crédito") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.tasaAnual,
                onValueChange = { viewModel.onTasaChanged(it) },
                label = { Text("Tasa anual (%)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.meses,
                onValueChange = { viewModel.onMesesChanged(it) },
                label = { Text("Meses") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.calcularCredito() },
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
