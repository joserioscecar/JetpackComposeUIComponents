import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.fluxis.trm.ui.viewmodel.TrmViewModel

@Composable
fun TrmScreen(viewModel: TrmViewModel = viewModel()) {
    val uiState = viewModel.uiState // Obtenemos el estado actual

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { viewModel.obtenerTrm() },
            enabled = !uiState.isLoading
        ) {
            Text(if (uiState.isLoading) "Consultando..." else "Consultar TRM")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.valorTrm != null) {
            Text(
                text = "TRM: ${uiState.valorTrm} ${uiState.moneda}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}