package co.fluxis.trm.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.fluxis.trm.data.api.RetrofitClient
import kotlinx.coroutines.launch

// Definición del estado de la UI
data class TrmUiState(
    val valorTrm: Double? = null,
    val moneda: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class TrmViewModel : ViewModel() {

    // Estado único observable
    var uiState by mutableStateOf(TrmUiState())
        private set

    fun obtenerTrm() {
        viewModelScope.launch {
            // Actualizamos el estado para indicar carga
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            try {
                val response = RetrofitClient.api.getTrmByDate("2025-12-23")
                // Actualizamos con los datos recibidos
                uiState = uiState.copy(
                    valorTrm = response.data.value,
                    moneda = response.data.unit,
                    isLoading = false
                )
            } catch (e: Exception) {
                e.printStackTrace()
                // Actualizamos con el error
                uiState = uiState.copy(
                    errorMessage = "Error al obtener TRM: ${e.message}",
                    isLoading = false
                )
            }
        }
    }
}