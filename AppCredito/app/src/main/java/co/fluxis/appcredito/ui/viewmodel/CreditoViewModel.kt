package co.fluxis.appcredito.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.fluxis.appcredito.ui.state.CreditoUiState
import kotlin.math.pow

class CreditoViewModel : ViewModel() {

    var uiState by mutableStateOf(CreditoUiState())
        private set

    fun onMontoChanged(monto: String) {
        uiState = uiState.copy(monto = monto)
    }

    fun onTasaChanged(tasa: String) {
        uiState = uiState.copy(tasaAnual = tasa)
    }

    fun onMesesChanged(meses: String) {
        uiState = uiState.copy(meses = meses)
    }

    fun calcularCredito() {
        val monto = uiState.monto.toDoubleOrNull() ?: 0.0
        val tasaAnual = uiState.tasaAnual.toDoubleOrNull() ?: 0.0
        val meses = uiState.meses.toIntOrNull() ?: 0

        if (monto > 0 && tasaAnual > 0 && meses > 0) {
            val tasaMensual = (tasaAnual / 100) / 12
            val cuota = (monto * tasaMensual) / (1 - (1 + (tasaMensual)).pow(-meses.toDouble()))
            val totalPagado = cuota * meses
            val intereses = totalPagado - monto

            uiState = uiState.copy(
                cuota = cuota,
                totalPagado = totalPagado,
                intereses = intereses
            )
        }
    }
}
