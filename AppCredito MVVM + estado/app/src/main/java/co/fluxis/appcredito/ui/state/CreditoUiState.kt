package co.fluxis.appcredito.ui.state

data class CreditoUiState(
    val cuota: Double = 0.0,
    val totalPagado: Double = 0.0,
    val intereses: Double = 0.0
)
