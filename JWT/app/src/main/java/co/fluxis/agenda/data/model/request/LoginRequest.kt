package co.fluxis.agenda.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val usuario: String,
    val clave: String
)