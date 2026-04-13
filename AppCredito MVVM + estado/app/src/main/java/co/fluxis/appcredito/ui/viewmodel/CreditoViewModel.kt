package co.fluxis.appcredito.ui.viewmodel

import androidx.lifecycle.ViewModel
import co.fluxis.appcredito.model.Credito
import co.fluxis.appcredito.ui.state.CreditoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreditoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreditoUiState())
    val uiState: StateFlow<CreditoUiState> = _uiState

    fun calcularCredito(credito: Credito) {
        val tasaMensual = (credito.tasaAnual / 100) / 12
        val n = credito.meses
        val p = credito.monto

        val cuota = p * (tasaMensual * Math.pow(1 + tasaMensual, n.toDouble())) /
                (Math.pow(1 + tasaMensual, n.toDouble()) - 1)

        val total = cuota * n
        val intereses = total - p

        _uiState.value = CreditoUiState(
            cuota = cuota,
            totalPagado = total,
            intereses = intereses
        )
    }
}
